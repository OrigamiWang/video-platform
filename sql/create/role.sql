CREATE TABLE `role`
(
    `id`   int         NOT NULL AUTO_INCREMENT,
    `name` varchar(10) NOT NULL COMMENT '角色名',
    `describe` varchar(50) not null COMMENT '角色的描述'
    PRIMARY KEY (`id`)
);