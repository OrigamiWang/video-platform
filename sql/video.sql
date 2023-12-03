CREATE TABLE `video` (
                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频id',
                         `url` varchar(255) NOT NULL DEFAULT 'www.test.jpg' COMMENT '封面url',
                         `play_num` int(11) NOT NULL DEFAULT '0' COMMENT '播放数量',
                         `dm_num` int(11) NOT NULL DEFAULT '0' COMMENT '弹幕数量',
                         `total_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总时长',
                         `title` varchar(50) NOT NULL COMMENT '标题',
                         `pid` int(11) NOT NULL DEFAULT '1' COMMENT '分区id',
                         `star_num` int(11) NOT NULL DEFAULT '0' COMMENT '收藏数',
                         `coin_num` int(11) NOT NULL DEFAULT '0' COMMENT '投币数量',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

