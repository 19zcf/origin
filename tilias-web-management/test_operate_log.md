# 操作日志功能测试说明

## 测试步骤

### 1. 启动应用
确保Spring Boot应用正常启动，数据库连接正常。

### 2. 创建数据库表
执行 `src/main/resources/sql/operate_log_table.sql` 创建操作日志表。

### 3. 测试登录
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "123456"
  }'
```

### 4. 测试操作日志记录
使用返回的Token调用增删改接口：

```bash
# 添加班级
curl -X POST http://localhost:8080/clazzs \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "测试班级",
    "room": "A101",
    "beginDate": "2024-01-01",
    "endDate": "2024-12-31",
    "subject": "Java"
  }'

# 修改班级
curl -X PUT http://localhost:8080/clazzs \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "修改后的班级",
    "room": "A102"
  }'

# 删除班级
curl -X DELETE http://localhost:8080/clazzs/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### 5. 验证日志记录
查询数据库中的 `operate_log` 表，应该能看到相应的操作日志记录：

```sql
SELECT * FROM operate_log ORDER BY operate_time DESC;
```

## 预期结果

1. **登录成功**：返回包含Token的响应
2. **操作日志记录**：每次调用增删改接口都会在 `operate_log` 表中记录一条日志
3. **用户信息正确**：`operate_emp_id` 字段应该包含正确的用户ID，不再是null
4. **日志信息完整**：包含操作人、操作时间、目标类、方法名、参数、返回值、执行时长等信息

## 常见问题排查

### 问题1：operate_emp_id 为 null
**原因**：Token解析失败或用户上下文未正确设置
**解决**：
1. 检查请求头中的Token格式是否正确：`Authorization: Bearer <token>`
2. 检查Token是否过期
3. 查看应用日志，确认TokenInterceptor是否正常工作

### 问题2：没有记录操作日志
**原因**：AOP切面未生效或数据库操作失败
**解决**：
1. 确认方法上已添加 `@OperateLog` 注解
2. 检查 `@EnableAspectJAutoProxy` 配置
3. 查看应用日志，确认AOP切面是否正常执行

### 问题3：日志记录不完整
**原因**：参数或返回值过长，被截断
**解决**：
1. 检查数据库字段长度限制
2. 调整 `OperateLogAspect` 中的截断逻辑

## 调试建议

1. **开启DEBUG日志**：在 `application.yml` 中设置日志级别为DEBUG
2. **查看TokenInterceptor日志**：确认用户上下文设置是否成功
3. **查看OperateLogAspect日志**：确认日志记录过程是否正常
4. **数据库监控**：直接查询 `operate_log` 表验证数据是否正确插入 