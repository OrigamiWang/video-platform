CREATE TABLE `partition`
(
    `id`   int         NOT NULL AUTO_INCREMENT COMMENT '分区id',
    `name` varchar(10) NOT NULL COMMENT '分区名',
    PRIMARY KEY (`id`)
);

insert INTO partition (name) VALUES ('non');