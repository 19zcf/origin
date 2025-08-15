-- 创建班级表
CREATE TABLE `clazz` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `name` varchar(100) NOT NULL COMMENT '班级名称',
  `room` varchar(50) DEFAULT NULL COMMENT '班级教室',
  `begin_date` date DEFAULT NULL COMMENT '开课时间',
  `end_date` date DEFAULT NULL COMMENT '结课时间',
  `master_id` int DEFAULT NULL COMMENT '班主任(员工ID)',
  `status` varchar(20) DEFAULT '未开班' COMMENT '班级状态：未开班、在读、已结课',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_master_id` (`master_id`),
  KEY `idx_begin_date` (`begin_date`),
  KEY `idx_end_date` (`end_date`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班级表';

-- 插入测试数据
INSERT INTO `clazz` (`name`, `room`, `begin_date`, `end_date`, `master_id`, `status`) VALUES
('黄埔四期', '209', '2023-08-01', '2024-02-15', 7, '已开班'),
('JavaEE就业166期', '105', '2023-07-20', '2024-02-20', 20, '已开班'),
('Python全栈开发班', '301', '2023-09-01', '2024-03-01', 15, '在读'),
('前端开发精英班', '205', '2023-10-01', '2024-04-01', 12, '未开班'),
('大数据分析班', '401', '2023-11-01', '2024-05-01', 18, '未开班'),
('人工智能基础班', '501', '2023-12-01', '2024-06-01', 25, '未开班'); 