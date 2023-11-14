DROP TABLE `user_detail`;
CREATE TABLE `user_detail`
(
    `id`     INT          NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `uid`    INT          NOT NULL COMMENT '用户id',
    `gender` BIT(2)       NOT NULL DEFAULT b'0' COMMENT '性别',
    `exp`    INT          NOT NULL DEFAULT 0 COMMENT '经验值',
    `ip`     VARCHAR(5)   NULL     DEFAULT NULL COMMENT 'ip属地',
    `info`   VARCHAR(100) NULL     DEFAULT NULL COMMENT '自我介绍'
);