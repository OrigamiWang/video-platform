CREATE TABLE `role_permission`
(
    `id`  int NOT NULL AUTO_INCREMENT,
    `rid` int NOT NULL COMMENT '角色id',
    `pid` int NOT NULL COMMENT '权限id',
    PRIMARY KEY (`id`)
);