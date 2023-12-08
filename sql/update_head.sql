CREATE TABLE `update_heat` (
                               `update_id` int(11) NOT NULL COMMENT '动态id',
                               `like_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数量',
                               `comment_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '评论数量',
                               `share_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '转发数量',
                               PRIMARY KEY (`update_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;