`user_msg`CREATE TABLE `history` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL COMMENT '用户索引',
    `media_type` INT NOT NULL COMMENT '媒体类型',
    `media_id` INT NOT NULL COMMENT '媒体索引',
    `watched_at` TIME COMMENT '视频观看时间戳',
    `his_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '历史访问时间'
);
-- only video has column `watched_at`
INSERT INTO history(user_id, media_type, media_id, watched_at)
VALUES(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21'),
(1, 0, 1, '00:01:21');

update history set watched_at = '00:00:05'
where user_id = 1
and media_id = 2
and media_type = 0

-- 约定：
-- 0: video
-- 1: update
-- 2: reserved type(eg: live)

select * from history
where user_id = 1
and media_type = 0
order by his_time DESC
limit 0, 20

select * from history
limit 5, 5