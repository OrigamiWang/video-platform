drop table `role`;
CREATE TABLE `role`
(
    `id`       int         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`     varchar(10) NOT NULL COMMENT '角色名',
    `describe` varchar(50) not null COMMENT '角色的描述'
);

INSERT INTO role(`name`, `describe`)
VALUES ('普通用户', '能够登录网站浏览视频、关注用户、发布视频、评论'),
       ('网站管理员', '管理用户的角色分配，能为其他用户分配除管理员外的其他身份'),
       ('会员购商家', '能在会员购页面售出符合审批商品'),
       ('会员购买家', '能在会员购页面购买商品'),
       ('大会员', '拥有1080p视频观看、发送彩色弹幕、粉红色昵称等权限');
