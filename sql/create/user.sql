CREATE TABLE `user`
(
    `id`       INT         NOT NULL AUTO_INCREMENT COMMENT 'uid',
    `name`     VARCHAR(10) NOT NULL COMMENT '昵称',
    `gender`   BIT(2)      NOT NULL DEFAULT 0 COMMENT '性别',
    `level`    INT         NOT NULL DEFAULT 0 COMMENT '等级',
    `exp`      INT         NOT NULL DEFAULT 0 COMMENT '经验值',
    `status`   INT(2)      NOT NULL DEFAULT 0 COMMENT '用户目前的状态',
    `role`     INT(2)      NOT NULL DEFAULT 0 COMMENT '用户身份',
    `fan`      INT         NOT NULL DEFAULT 0 COMMENT '粉丝数量',
    `follow`   INT         NOT NULL DEFAULT 0 COMMENT '关注数量',
    `like`     INT         NOT NULL DEFAULT 0 COMMENT '获赞数量',
    `ip`       VARCHAR(5)           DEFAULT NULL COMMENT 'ip属地',
    `phone`    VARCHAR(11)          DEFAULT NULL COMMENT '电话',
    `username` VARCHAR(20)          DEFAULT NULL COMMENT '账号',
    `email`    VARCHAR(30)          DEFAULT NULL COMMENT '邮箱',
    `password` CHAR(64)    NOT NULL COMMENT '密码',
    `info`     VARCHAR(100)         DEFAULT NULL COMMENT '自我介绍',
    PRIMARY KEY (`id`)
);