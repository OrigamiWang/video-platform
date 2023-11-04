INSERT INTO `user`(`name`, `phone`, `password`)
VALUES ('御坂', '13880970270', 'abc123');

SELECT *
FROM `user`
WHERE phone = '13880970270';

DROP TABLE `user`