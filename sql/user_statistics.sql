CREATE TABLE `user_statistics` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `uid` int(11) NOT NULL COMMENT '用户id',
                                   `fan` int(11) NOT NULL DEFAULT '0' COMMENT '粉丝数量',
                                   `follow` int(11) NOT NULL DEFAULT '0' COMMENT '关注数量',
                                   `like` int(11) NOT NULL DEFAULT '0' COMMENT '获赞数量',
                                   `video_num` int(11) NOT NULL DEFAULT '0' COMMENT '投稿视频数量',
                                   `video_total_play` int(11) NOT NULL DEFAULT '0' COMMENT '视频总播放量',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;