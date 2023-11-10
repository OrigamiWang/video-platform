CREATE TABLE `visit`(
     `id` INT NOT NULL AUTO_INCREMENT,
     `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新时间',
     `api` VARCHAR(255) NOT NULL COMMENT 'api接口，all为总量',
     `visits` BIGINT(20) NOT NULL COMMENT '访问量',
     `time_id` VARCHAR(255) NOT NULL COMMENT 'yyyy-mm-dd的日期id',
     PRIMARY KEY (`id`)
);