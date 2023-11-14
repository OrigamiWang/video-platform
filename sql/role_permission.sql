CREATE TABLE `role_permission`
(
    `id`  int NOT NULL AUTO_INCREMENT,
    `rid` int NOT NULL COMMENT '角色id',
    `pid` int NOT NULL COMMENT '权限id',
    PRIMARY KEY (`id`)
);

INSERT INTO role_permission(`rid`, `pid`)
VALUES (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5);



SELECT p.`id`
FROM permission p
WHERE p.`id` IN (SELECT rp.`pid`
                 FROM role_permission rp
                 WHERE rp.`rid` =
                       (SELECT ur.`rid`
                        FROM `user_role` ur
                        WHERE ur.`uid` = 1));