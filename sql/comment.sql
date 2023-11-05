DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `foreign_id` int(11) NOT NULL COMMENT '评论的对象id',
  `foreign_type` int(11) NOT NULL COMMENT '评论的对象类型',
  `pid` int(11) DEFAULT NULL COMMENT '父级评论的id',
  `target_username` varchar(20) DEFAULT NULL COMMENT '回复的用户',
  `createTime` datetime NOT NULL COMMENT '评论时间',
  `like_num` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `comment` (`user_id`,`username`,`content`,`foreign_id`,`foreign_type`,`createTime`)
VALUES ('1','xiaoming','hahahaha','1','1','2023-11-01 12:11:12');
