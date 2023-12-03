CREATE TABLE `video` (
                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频id',
                         `url` varchar(255) NOT NULL DEFAULT 'www.test.jpg' COMMENT '封面url',
                         `play_num` int(11) NOT NULL DEFAULT '0' COMMENT '播放数量',
                         `dm_num` int(11) NOT NULL DEFAULT '0' COMMENT '弹幕数量',
                         `total_time` varchar(15) NOT NULL DEFAULT '00:00' COMMENT '总时长',
                         `title` varchar(50) NOT NULL COMMENT '标题',
                         `pid` int(11) NOT NULL DEFAULT '1' COMMENT '分区id',
                         `starNum` int(11) NOT NULL DEFAULT '0' COMMENT '收藏数',
                         `coinNum` int(11) NOT NULL DEFAULT '0' COMMENT '投币数量',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


INSERT INTO `video`(`title`) VALUES ('御坂美琴');
INSERT INTO `video`(`title`) VALUES ('深圳大学，世界第一大学');
INSERT INTO `video`(`title`) VALUES ('南区饭堂十分美味');
INSERT INTO `video`(`title`) VALUES ('南区宿舍住宿环境非常好');
INSERT INTO `video`(`title`) VALUES ('我爱深大！');
INSERT INTO `video`(`title`) VALUES ('深大爱我！');
INSERT INTO `video`(`title`) VALUES ('一方通行');
INSERT INTO `video`(`title`) VALUES ('某科学的超电磁炮');
INSERT INTO `video`(`title`) VALUES ('【全网首发】xx秒速通深圳大学');
INSERT INTO `video`(`title`) VALUES ('【原神】为什么我不推荐抽芙宁娜');
INSERT INTO `video`(`title`) VALUES ('连一刻也没有为五条悟的死而哀悼，赶到现场的是xxx');
INSERT INTO `video`(`title`) VALUES ('《大 学 期 末 新 手 教 程》');
INSERT INTO `video`(`title`) VALUES ('三亿人在豆瓣上打出一坤分');
INSERT INTO `video`(`title`) VALUES ('爆肝3天！200%还原《猫和老鼠》里的钻洞汤姆');


CREATE TABLE `updates` (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '动态id',
                           `vid` int(11) NOT NULL COMMENT '视频id，默认0表示普通动态，其余指向视频',
                           `uid` int(11) NOT NULL DEFAULT '1' COMMENT '动态、视频作者id',
                           `content` varchar(255) NOT NULL DEFAULT '' COMMENT '动态正文',
                           `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态',
                           `dateTime` uploadTime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
                           `urls` varchar(255) NOT NULL DEFAULT '' COMMENT '动态图文路径、视频封面、视频资源路径',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

INSERT INTO `updates`(`vid`, `uid`) VALUES (3, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (4, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (5, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (6, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (7, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (8, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (9, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (10, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (11, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (12, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (13, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (14, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (15, 1);
INSERT INTO `updates`(`vid`, `uid`) VALUES (16, 1);