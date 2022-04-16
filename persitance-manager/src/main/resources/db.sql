SELECT * FROM reviewdb.user;

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
