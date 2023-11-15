CREATE TABLE `permission`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL COMMENT '权限名',
    PRIMARY KEY (`id`)
);

INSERT INTO permission(`name`)
VALUES ('查看个人主页'),
       ('删除视频'),
       ('上架商品'),
       ('观看1080高码率视频'),
       ('观看大会员视频');

