

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.javaadmintemplate.entity.User;
import com.example.javaadmintemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户控制器
 * RESTful风格API
 *
 * @author example
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 获取用户列表（分页）
     * GET /api/users?page=1&size=10
     */
    @GetMapping
    public ResponseEntity<IPage<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<User> userPage = new Page<>(page, size);
        IPage<User> userList = userService.page(userPage);

        return ResponseEntity.ok(userList);
    }

    /**
     * 根据ID获取用户详情
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 创建新用户
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    /**
     * 更新用户信息
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (userService.count(new QueryWrapper<User>().eq("id", id)) == 0) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return ResponseEntity.ok(user);
    }

    /**
     * 删除用户
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.count(new QueryWrapper<User>().eq("id", id)) == 0) {
            return ResponseEntity.notFound().build();
        }
        userService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 批量删除用户
     * DELETE /api/users
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Long> ids) {
        userService.removeByIds(ids);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据用户名查询用户
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<List<User>> getUserByUsername(@PathVariable String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        List<User> users = userService.list(wrapper);
        return ResponseEntity.ok(users);
    }

    /**
     * 更新用户状态
     * PATCH /api/users/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<User> updateUserStatus(@PathVariable Long id, @RequestBody Integer status) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return ResponseEntity.ok(user);
    }

}