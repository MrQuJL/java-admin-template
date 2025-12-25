#!/bin/bash

echo "=== 用户管理功能测试 ==="

# 1. 创建新用户
echo "1. 创建新用户..."
create_response=$(curl -s -X POST http://localhost:8081/admin/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123","realName":"测试用户","phone":"13800138003","email":"test@example.com","status":1}')

if [ $? -eq 0 ]; then
  user_id=$(echo $create_response | jq -r '.id')
  echo "   ✓ 成功创建用户，ID: $user_id"
  echo "   创建结果: $create_response"
else
  echo "   ✗ 创建用户失败"
fi

# 2. 查询用户列表
echo -e "\n2. 查询用户列表..."
list_response=$(curl -s -X GET http://localhost:8081/admin/api/users)
if [ $? -eq 0 ]; then
  total=$(echo $list_response | jq -r '.total')
  echo "   ✓ 成功获取用户列表，共 $total 个用户"
  echo "   用户列表: $list_response"
else
  echo "   ✗ 查询用户列表失败"
fi

# 3. 更新用户信息
echo -e "\n3. 更新用户信息..."
update_response=$(curl -s -X PUT http://localhost:8081/admin/api/users/$user_id \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser_updated","realName":"更新后的测试用户"}')

if [ $? -eq 0 ]; then
  echo "   ✓ 成功更新用户信息"
  echo "   更新结果: $update_response"
else
  echo "   ✗ 更新用户信息失败"
fi

# 4. 查询单个用户
echo -e "\n4. 查询单个用户..."
get_response=$(curl -s -X GET http://localhost:8081/admin/api/users/$user_id)
if [ $? -eq 0 ]; then
  echo "   ✓ 成功获取用户详情"
  echo "   用户详情: $get_response"
else
  echo "   ✗ 查询用户详情失败"
fi

# 5. 删除用户
echo -e "\n5. 删除用户..."
delete_response=$(curl -s -X DELETE http://localhost:8081/admin/api/users/$user_id)
if [ $? -eq 0 ]; then
  echo "   ✓ 成功删除用户"
else
  echo "   ✗ 删除用户失败"
fi

# 6. 验证删除结果
echo -e "\n6. 验证删除结果..."
final_list=$(curl -s -X GET http://localhost:8081/admin/api/users)
final_total=$(echo $final_list | jq -r '.total')
echo "   ✓ 删除后用户总数: $final_total"

echo -e "\n=== 测试完成 ==="