CREATE TABLE `updates` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '动态id',
                           `uid` int NOT NULL COMMENT '发布者的用户id',
                           `title` varchar(40) NOT NULL COMMENT '标题',
                           `content` varchar(1024) NOT NULL COMMENT '中文',
                           `type` varchar(10) NOT NULL COMMENT '视频类型',
                           `dateTime` timestamp NOT NULL COMMENT '时间',
                           `urls` varchar(1024) DEFAULT NULL COMMENT '多媒体urls的json',
                           PRIMARY KEY (`id`),
                           FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
);

