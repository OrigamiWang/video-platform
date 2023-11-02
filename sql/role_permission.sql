DROP TABLE `permission`;
CREATE TABLE `permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL COMMENT '权限名',
  PRIMARY KEY (`id`)
);

INSERT INTO permission(`name`)
VALUES
('查看个人主页'),
('删除视频'),
('上架商品'),
('观看1080高码率视频'),
('观看大会员视频');


INSERT INTO role(`name`)
VALUES
('普通用户'),
('网站管理员'),
('会员购商家'),
('会员购买家'),
('大会员');


INSERT INTO role_permission(`rid`, `pid`)
VALUES
(2,1),
(2,2),
(2,3),
(2,4),
(2,5);

INSERT INTO user_role(`uid`,`rid`)
VALUES(1,2);


SELECT p.`id`
FROM permission p
	WHERE p.`id` IN (
	SELECT rp.`pid`
	FROM role_permission rp
	WHERE rp.`rid` = 
	(
		SELECT ur.`rid`
		FROM `user_role` ur
		WHERE ur.`uid` = 1
	)
);