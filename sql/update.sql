CREATE TABLE `update`
(
    `id`       int           NOT NULL AUTO_INCREMENT COMMENT '动态id',
    `uid`      int           NOT NULL COMMENT '发布者的用户id',
    `title`    varchar(40)   NOT NULL COMMENT '标题',
    `content`  varchar(1024) NOT NULL COMMENT '正文',
    `status`   int           NOT NULL COMMENT '状态码,状态码：0-未通过审核；1-通过审核；2-已下架',
    `type`     int           NOT NULL COMMENT '动态类型',
    `upload_time` timestamp     NOT NULL COMMENT '时间',
    `urls`     varchar(1024) DEFAULT NULL COMMENT '多媒体urls的json',
    `pid`      int           NOT NULL COMMENT '分区id',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
    FOREIGN KEY (`pid`) REFERENCES `update_partition` (`id`)
);

