DROP TABLE `user`;
CREATE TABLE `user`
(
    `id`       INT         NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'uid',
    `name`     VARCHAR(10) NOT NULL COMMENT '昵称',
    `level`    INT         NOT NULL DEFAULT 0 COMMENT '等级',
    `status`   INT         NOT NULL DEFAULT 0 COMMENT '用户状态',
    `password` CHAR(64)    NULL COMMENT '密码',
    `phone`    VARCHAR(15) NULL COMMENT '手机号',
    `email`    VARCHAR(30) NULL COMMENT '邮箱'
);

INSERT INTO `user`(`name`, `phone`, `password`)
VALUES ('御坂', '13880970270', 'abc123');

SELECT *
FROM `user`
WHERE phone = '13880970270';
