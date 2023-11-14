CREATE TABLE `user_statistics`
(
    `id`     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `uid`    INT NOT NULL COMMENT '用户id',
    `fan`    INT NOT NULL DEFAULT 0 COMMENT '粉丝数量',
    `follow` INT NOT NULL DEFAULT 0 COMMENT '关注数量',
    `like`   INT NOT NULL DEFAULT 0 COMMENT '获赞数量'
);