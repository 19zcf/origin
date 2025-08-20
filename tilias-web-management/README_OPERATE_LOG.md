# 操作日志功能使用说明

## 功能概述

基于Spring AOP实现的操作日志记录功能，可以自动记录系统中所有增、删、改功能接口的操作日志。

## 功能特性

1. **自动记录**：通过AOP切面自动拦截带有`@OperateLog`注解的方法
2. **完整信息**：记录操作人、操作时间、目标类、方法名、参数、返回值、执行时长等
3. **性能优化**：异步记录日志，不影响主业务流程
4. **灵活配置**：支持自定义操作描述和操作类型

## 日志信息包含

- **操作人ID**：当前登录用户的ID
- **操作时间**：方法执行的时间
- **目标类名**：被拦截方法的全类名
- **方法名**：被拦截的方法名
- **方法参数**：方法运行时的参数（JSON格式）
- **返回值**：方法的返回值（JSON格式）
- **执行时长**：方法执行耗时（毫秒）

## 使用方法

### 1. 登录获取Token

首先需要调用登录接口获取JWT Token：

```bash
POST /login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

登录成功后会返回包含Token的响应：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "username": "admin",
    "name": "管理员",
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  }
}
```

### 2. 在需要记录日志的方法上添加注解

```java
@PostMapping
@OperateLog(value = "添加用户", operateType = "增")
public Result save(@RequestBody User user) {
    // 业务逻辑
    return Result.success();
}

@PutMapping
@OperateLog(value = "修改用户", operateType = "改")
public Result update(@RequestBody User user) {
    // 业务逻辑
    return Result.success();
}

@DeleteMapping("/{id}")
@OperateLog(value = "删除用户", operateType = "删")
public Result delete(@PathVariable Integer id) {
    // 业务逻辑
    return Result.success();
}
```

### 3. 发送请求时携带Token

在调用需要记录日志的接口时，需要在请求头中携带Token：

```bash
POST /clazzs
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "name": "Java基础班",
  "room": "A101"
}
```

### 4. 注解参数说明

- **value**：操作描述，用于说明具体的操作内容
- **operateType**：操作类型，建议使用"增"、"删"、"改"、"查"等

### 5. 已配置的Controller

以下Controller已经配置了操作日志注解：

- **ClazzController**：班级管理
- **EmpController**：员工管理  
- **StudentController**：学员管理
- **DeptController**：部门管理

## 数据库表结构

```sql
create table operate_log(
    id int unsigned primary key auto_increment comment 'ID',
    operate_emp_id int unsigned comment '操作人ID',
    operate_time datetime comment '操作时间',
    class_name varchar(100) comment '操作的类名',
    method_name varchar(100) comment '操作的方法名',
    method_params varchar(2000) comment '方法参数',
    return_value varchar(2000) comment '返回值',
    cost_time bigint unsigned comment '方法执行耗时, 单位:ms'
) comment '操作日志表';
```

## 核心组件

### 1. OperateLog注解
- 位置：`com.zcf.annotation.OperateLog`
- 作用：标记需要记录操作日志的方法

### 2. OperateLogAspect切面
- 位置：`com.zcf.aspect.OperateLogAspect`
- 作用：拦截方法执行，记录操作日志

### 3. UserContext工具类
- 位置：`com.zcf.utils.UserContext`
- 作用：获取当前登录用户信息

### 4. TokenInterceptor拦截器
- 位置：`com.zcf.interceptor.TokenInterceptor`
- 作用：自动解析JWT Token并设置用户上下文

### 5. OperateLogMapper
- 位置：`com.zcf.mapper.OperateLogMapper`
- 作用：操作日志数据持久化

## 注意事项

1. **JWT Token**：确保在请求头中包含有效的JWT Token，格式：`Authorization: Bearer <token>`
2. **用户认证**：TokenInterceptor会自动解析Token并设置用户上下文，无需手动调用
3. **参数长度**：方法参数和返回值会自动截断，避免数据库字段溢出
4. **异常处理**：即使方法执行异常，也会记录操作日志
5. **性能影响**：AOP切面会轻微影响方法执行性能，但日志记录是异步的

## 扩展功能

如需添加更多功能，可以：

1. 在`OperateLog`注解中添加更多属性
2. 在`OperateLogAspect`中添加更多日志信息
3. 扩展`OperateLog`实体类，添加更多字段
4. 实现日志查询和管理功能

## 示例日志

```json
{
    "id": 1,
    "operateEmpId": 1001,
    "operateTime": "2024-01-15 10:30:00",
    "className": "com.zcf.controller.ClazzController",
    "methodName": "save",
    "methodParams": "[{\"name\":\"Java基础班\",\"room\":\"A101\"}]",
    "returnValue": "{\"code\":200,\"message\":\"操作成功\"}",
    "costTime": 150
}
``` 