CREATE TABLE `user_role`
(
    `id`  int NOT NULL AUTO_INCREMENT,
    `uid` int NOT NULL COMMENT '用户id',
    `rid` int NOT NULL COMMENT '角色id',
    PRIMARY KEY (`id`)
);