-- 操作日志表
CREATE TABLE `operate_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_emp_id` int unsigned COMMENT '操作人ID',
  `operate_time` datetime COMMENT '操作时间',
  `class_name` varchar(100) COMMENT '操作的类名',
  `method_name` varchar(100) COMMENT '操作的方法名',
  `method_params` varchar(2000) COMMENT '方法参数',
  `return_value` varchar(2000) COMMENT '返回值',
  `cost_time` bigint unsigned COMMENT '方法执行耗时, 单位:ms',
  PRIMARY KEY (`id`),
  KEY `idx_operate_emp_id` (`operate_emp_id`),
  KEY `idx_operate_time` (`operate_time`),
  KEY `idx_class_name` (`class_name`)
) COMMENT='操作日志表';

-- 插入测试数据（可选）
-- INSERT INTO `operate_log` (`operate_emp_id`, `operate_time`, `class_name`, `method_name`, `method_params`, `return_value`, `cost_time`) 
-- VALUES (1, NOW(), 'com.zcf.controller.ClazzController', 'save', '[{"name":"测试班级","room":"A101"}]', '{"code":200,"message":"操作成功"}', 150); 