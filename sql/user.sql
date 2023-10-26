CREATE TABLE `user`
(
    `id`       int         NOT NULL AUTO_INCREMENT COMMENT 'uid',
    `name`     varchar(10) NOT NULL COMMENT '昵称',
    `gender`   bit(1)       DEFAULT NULL COMMENT '性别',
    `level`    int         NOT NULL COMMENT '等级',
    `exp`      int         NOT NULL COMMENT '经验值',
    `status`   bit(2)      NOT NULL COMMENT '用户目前的状态',
    `role`     bit(2)      NOT NULL COMMENT '用户身份',
    `fan`      int         NOT NULL COMMENT '粉丝数量',
    `follow`   int         NOT NULL COMMENT '关注数量',
    `like`     int         NOT NULL COMMENT '获赞数量',
    `ip`       varchar(5)   DEFAULT NULL COMMENT 'ip属地',
    `phone`    varchar(11) NOT NULL COMMENT '电话/账号',
    `password` varchar(20)  DEFAULT NULL COMMENT '密码',
    `info`     varchar(100) DEFAULT NULL COMMENT '自我介绍',
    PRIMARY KEY (`id`)
)