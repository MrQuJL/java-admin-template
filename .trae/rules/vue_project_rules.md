# Vue 项目通用规则

## 项目结构规范

### 1. 目录结构
```
src/
├── api/                # API 接口请求
├── assets/             # 静态资源
├── components/         # 通用组件
│   ├── common/        # 公共组件
│   ├── business/      # 业务组件
│   └── ui/           # UI 组件
├── composables/       # 组合式函数
├── directives/        # 自定义指令
├── layouts/          # 布局组件
├── middleware/       # 中间件
├── pages/            # 页面组件（路由页面）
├── plugins/          # 插件配置
├── router/           # 路由配置
├── stores/           # 状态管理
├── styles/           # 全局样式
│   ├── variables.scss
│   ├── mixins.scss
│   └── global.scss
├── utils/             # 工具函数
├── App.vue
└── main.js
```

### 2. 文件命名规范
- **组件文件**: 使用 PascalCase，如 `UserList.vue`, `ProductCard.vue`
- **JS/TS 文件**: 使用 camelCase，如 `userService.js`, `dateUtils.js`
- **常量文件**: 使用 UPPER_SNAKE_CASE，如 `API_CONSTANTS.js`
- **目录名**: 使用 kebab-case，如 `user-management`, `product-detail`

## Vue 组件规范

### 1. 单文件组件结构
```vue
<template>
  <!-- 模板内容 -->
</template>

<script setup>
// Vue 3 组合式 API 语法
import { ref, computed, watch, onMounted } from 'vue'

// Props 定义
const props = defineProps({
  userId: {
    type: Number,
    required: true
  },
  disabled: {
    type: Boolean,
    default: false
  }
})

// Emits 定义
const emit = defineEmits(['update', 'delete'])

// 响应式数据
const user = ref(null)
const loading = ref(false)
const error = ref(null)

// 计算属性
const fullName = computed(() => {
  return user.value ? `${user.value.firstName} ${user.value.lastName}` : ''
})

// 方法
const fetchUser = async () => {
  loading.value = true
  try {
    user.value = await getUser(props.userId)
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}

// 生命周期
onMounted(() => {
  fetchUser()
})
</script>

<style scoped>
/* 组件样式 */
</style>
```

### 2. 组件命名规范
```vue
<!-- 好的命名 -->
<UserProfile />
<ProductList />
<NavigationMenu />

<!-- 不好的命名 -->
<user />
<product />
<nav />
```

### 3. Props 规范
```javascript
const props = defineProps({
  // 基础类型
  title: String,
  
  // 多种类型
  value: [String, Number],
  
  // 详细配置
  user: {
    type: Object,
    required: true,
    validator: (value) => {
      return value.id && value.name
    }
  },
  
  // 默认值
  size: {
    type: String,
    default: 'medium',
    validator: (value) => ['small', 'medium', 'large'].includes(value)
  },
  
  // 对象/数组默认值
  config: {
    type: Object,
    default: () => ({
      theme: 'light',
      showHeader: true
    })
  }
})
```

## 状态管理规范

### 1. Pinia Store 结构（Vue 3）
```javascript
// stores/user.js
import { defineStore } from 'pinia'
import { getUser, updateUser } from '@/api/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
    loading: false,
    error: null
  }),
  
  getters: {
    isLoggedIn: (state) => !!state.user,
    fullName: (state) => state.user ? `${state.user.firstName} ${state.user.lastName}` : '',
    
    // 使用其他 getter
    displayName: (state) => {
      return state.user?.nickname || state.fullName || 'Anonymous'
    }
  },
  
  actions: {
    async fetchUser(userId) {
      this.loading = true
      this.error = null
      
      try {
        this.user = await getUser(userId)
      } catch (error) {
        this.error = error.message
        throw error
      } finally {
        this.loading = false
      }
    },
    
    async updateUserProfile(userData) {
      try {
        const updatedUser = await updateUser(this.user.id, userData)
        this.user = updatedUser
        return updatedUser
      } catch (error) {
        this.error = error.message
        throw error
      }
    },
    
    logout() {
      this.user = null
      this.error = null
    }
  }
})
```

### 2. 使用规范
```vue
<template>
  <div>
    <div v-if="userStore.loading">Loading...</div>
    <div v-else-if="userStore.error">{{ userStore.error }}</div>
    <div v-else-if="userStore.isLoggedIn">
      <h1>Welcome, {{ userStore.displayName }}!</h1>
      <button @click="handleLogout">Logout</button>
    </div>
  </div>
</template>

<script setup>
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

// 使用 storeToRefs 保持响应性
const { user, loading, error } = storeToRefs(userStore)

const handleLogout = () => {
  userStore.logout()
}

// 在需要时调用 action
onMounted(() => {
  if (!userStore.isLoggedIn) {
    userStore.fetchUser(userId)
  }
})
</script>
```

## 路由规范

### 1. 路由配置
```javascript
// router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/pages/Home.vue'),
    meta: {
      title: '首页',
      requiresAuth: false
    }
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/pages/Login.vue'),
    meta: {
      title: '登录',
      requiresAuth: false,
      layout: 'auth'
    }
  },
  {
    path: '/dashboard',
    component: () => import('@/layouts/DashboardLayout.vue'),
    meta: {
      requiresAuth: true,
      roles: ['admin', 'user']
    },
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('@/pages/dashboard/Index.vue'),
        meta: {
          title: '控制台'
        }
      },
      {
        path: 'users',
        name: 'UserManagement',
        component: () => import('@/pages/dashboard/UserManagement.vue'),
        meta: {
          title: '用户管理',
          permissions: ['user:read', 'user:write']
        }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/pages/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore()
  
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - 管理系统` : '管理系统'
  
  // 验证登录状态
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
    return
  }
  
  // 验证角色权限
  if (to.meta.roles && !authStore.hasRoles(to.meta.roles)) {
    next('/403')
    return
  }
  
  next()
})

export default router
```

### 2. 路由使用规范
```vue
<template>
  <div>
    <!-- 使用 router-link -->
    <router-link to="/users" class="nav-link">用户管理</router-link>
    
    <!-- 带参数的路由 -->
    <router-link :to="{ name: 'UserDetail', params: { id: user.id } }">
      {{ user.name }}
    </router-link>
    
    <!-- 编程式导航 -->
    <button @click="goToUserDetail(user.id)">查看详情</button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goToUserDetail = (userId) => {
  router.push({
    name: 'UserDetail',
    params: { id: userId }
  })
}
</script>
```

## API 请求规范

### 1. API 服务封装
```javascript
// api/request.js
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import { ElMessage } from 'element-plus'

// 创建 axios 实例
const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore()
    if (authStore.token) {
      config.headers.Authorization = `Bearer ${authStore.token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    const { code, message, data } = response.data
    
    // 业务成功
    if (code === 'SUCCESS') {
      return data
    }
    
    // 业务失败
    ElMessage.error(message || '请求失败')
    return Promise.reject(new Error(message))
  },
  (error) => {
    const { response } = error
    
    if (response) {
      const { status, data } = response
      
      switch (status) {
        case 401:
          // 未授权，跳转到登录页
          useAuthStore().logout()
          router.push('/login')
          break
        case 403:
          ElMessage.error('没有权限访问')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error(data?.message || '请求失败')
      }
    } else {
      ElMessage.error('网络错误，请检查网络连接')
    }
    
    return Promise.reject(error)
  }
)

export default request
```

### 2. API 模块封装
```javascript
// api/user.js
import request from './request'

export const userApi = {
  // 获取用户列表
  getUserList(params) {
    return request.get('/users', { params })
  },
  
  // 获取用户信息
  getUserById(id) {
    return request.get(`/users/${id}`)
  },
  
  // 创建用户
  createUser(data) {
    return request.post('/users', data)
  },
  
  // 更新用户
  updateUser(id, data) {
    return request.put(`/users/${id}`, data)
  },
  
  // 删除用户
  deleteUser(id) {
    return request.delete(`/users/${id}`)
  }
}

// 使用方式
import { userApi } from '@/api/user'

const loadUsers = async () => {
  try {
    const users = await userApi.getUserList({
      page: 1,
      pageSize: 10
    })
    console.log(users)
  } catch (error) {
    console.error('Failed to load users:', error)
  }
}
```

## 样式规范

### 1. CSS 组织方式
```scss
/* styles/variables.scss */
// 颜色变量
$primary-color: #409eff;
$success-color: #67c23a;
$warning-color: #e6a23c;
$danger-color: #f56c6c;
$info-color: #909399;

// 字体变量
$font-size-base: 14px;
$font-size-large: 16px;
$font-size-small: 12px;

// 间距变量
$spacing-xs: 4px;
$spacing-sm: 8px;
$spacing-md: 16px;
$spacing-lg: 24px;
$spacing-xl: 32px;

/* styles/mixins.scss */
// 常用 mixin
@mixin flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

@mixin text-ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* styles/global.scss */
// 全局样式
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  font-size: $font-size-base;
  color: #333;
  background-color: #f5f5f5;
}
```

### 2. 组件样式规范
```vue
<template>
  <div class="user-card">
    <img class="user-card__avatar" :src="user.avatar" :alt="user.name" />
    <div class="user-card__info">
      <h3 class="user-card__name">{{ user.name }}</h3>
      <p class="user-card__email">{{ user.email }}</p>
    </div>
  </div>
</template>

<style scoped lang="scss">
.user-card {
  display: flex;
  align-items: center;
  padding: $spacing-md;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  
  &__avatar {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    margin-right: $spacing-md;
  }
  
  &__info {
    flex: 1;
  }
  
  &__name {
    font-size: $font-size-large;
    font-weight: 500;
    margin-bottom: $spacing-xs;
  }
  
  &__email {
    font-size: $font-size-small;
    color: $info-color;
    @include text-ellipsis;
  }
}
</style>
```

## 性能优化规范

### 1. 组件懒加载
```javascript
// 路由懒加载
const routes = [
  {
    path: '/dashboard',
    component: () => import('@/pages/Dashboard.vue')
  }
]

// 异步组件
import { defineAsyncComponent } from 'vue'

const AsyncComponent = defineAsyncComponent(() =>
  import('@/components/HeavyComponent.vue')
)
```

### 2. 计算属性和侦听器优化
```vue
<script setup>
import { ref, computed, watch } from 'vue'

const props = defineProps({
  items: Array,
  filter: String
})

// 使用计算属性缓存结果
const filteredItems = computed(() => {
  if (!props.filter) return props.items
  return props.items.filter(item => 
    item.name.toLowerCase().includes(props.filter.toLowerCase())
  )
})

// 使用 watch 而不是多次计算
const searchTerm = ref('')

watch(searchTerm, (newTerm, oldTerm) => {
  if (newTerm !== oldTerm) {
    // 执行搜索逻辑
    performSearch(newTerm)
  }
}, {
  debounce: 300 // 防抖处理
})
</script>
```

### 3. 虚拟滚动和长列表优化
```vue
<template>
  <RecycleScroller
    class="scroller"
    :items="list"
    :item-size="32"
    key-field="id"
    v-slot="{ item }">
    <div class="user">
      {{ item.name }}
    </div>
  </RecycleScroller>
</template>

<script setup>
import { RecycleScroller } from 'vue-virtual-scroller'
import 'vue-virtual-scroller/dist/vue-virtual-scroller.css'
</script>
```

## 测试规范

### 1. 组件测试
```javascript
// components/__tests__/UserCard.spec.js
import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import UserCard from '@/components/UserCard.vue'

describe('UserCard', () => {
  it('renders user information correctly', () => {
    const user = {
      id: 1,
      name: 'John Doe',
      email: 'john@example.com',
      avatar: 'avatar.jpg'
    }
    
    const wrapper = mount(UserCard, {
      props: { user }
    })
    
    expect(wrapper.find('.user-card__name').text()).toBe('John Doe')
    expect(wrapper.find('.user-card__email').text()).toBe('john@example.com')
    expect(wrapper.find('.user-card__avatar').attributes('src')).toBe('avatar.jpg')
  })
  
  it('emits delete event when delete button is clicked', async () => {
    const wrapper = mount(UserCard, {
      props: { user: { id: 1, name: 'John' } }
    })
    
    await wrapper.find('.delete-button').trigger('click')
    
    expect(wrapper.emitted('delete')).toBeTruthy()
    expect(wrapper.emitted('delete')[0]).toEqual([1])
  })
})
```

### 2. 测试覆盖率要求
- 组件渲染测试覆盖率 > 90%
- 用户交互测试覆盖率 > 80%
- 业务逻辑测试覆盖率 > 85%
- API 调用测试覆盖率 > 90%

## 部署和构建规范

### 1. 环境配置
```javascript
// .env.development
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_TITLE=My App (Dev)

// .env.production
VITE_API_BASE_URL=https://api.myapp.com
VITE_APP_TITLE=My App
```

### 2. 构建优化
```javascript
// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { visualizer } from 'rollup-plugin-visualizer'

export default defineConfig({
  plugins: [vue()],
  build: {
    rollupOptions: {
      output: {
        manualChunks: {
          'vue-vendor': ['vue', 'vue-router', 'pinia'],
          'ui-vendor': ['element-plus'],
          'utils': ['lodash', 'dayjs']
        }
      }
    },
    // 启用压缩
    minify: 'terser',
    terserOptions: {
      compress: {
        drop_console: true,
        drop_debugger: true
      }
    }
  },
  plugins: [
    vue(),
    visualizer({
      open: true,
      gzipSize: true,
      brotliSize: true
    })
  ]
})
```

### 3. 性能监控
```javascript
// utils/performance.js
export const performanceMonitor = {
  measure(name, fn) {
    const start = performance.now()
    const result = fn()
    const end = performance.now()
    console.log(`${name} took ${end - start} milliseconds`)
    return result
  },
  
  measureAsync(name, asyncFn) {
    return asyncFn().then(result => {
      const end = performance.now()
      console.log(`${name} took ${end - start} milliseconds`)
      return result
    })
  }
}

// 使用示例
import { performanceMonitor } from '@/utils/performance'

const loadData = async () => {
  return performanceMonitor.measureAsync('loadUserData', async () => {
    const response = await fetch('/api/users')
    return response.json()
  })
}
```