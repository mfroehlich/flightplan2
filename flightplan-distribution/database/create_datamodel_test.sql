DROP SCHEMA IF EXISTS flightplantest;
CREATE SCHEMA flightplantest;
USE flightplantest;

CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `version` int default 1 not null,
  `email` varchar(50) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT uc_user_username_unique UNIQUE (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `pilot` (
  `id` varchar(50) NOT NULL,
  `licence_validity` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_kp4l415fkxg688bg286x3cga5` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `aircrafttype` (
  `id` varchar(50) NOT NULL,
  `version` int default 1 not null,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `pilot_to_aircrafttype` (
  `pilot_id` varchar(50) NOT NULL,
  `aircrafttype_id` varchar(50) NOT NULL,
  PRIMARY KEY (`pilot_id`,`aircrafttype_id`),
  KEY `FK_g56mhw9sj2f3r6eltff9whlk8` (`aircrafttype_id`),
  CONSTRAINT `FK_g56mhw9sj2f3r6eltff9whlk8` FOREIGN KEY (`aircrafttype_id`) REFERENCES `aircrafttype` (`id`),
  CONSTRAINT `FK_pd7yfmmhx2wxqfm0hec24qeaq` FOREIGN KEY (`pilot_id`) REFERENCES `pilot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `reservation_item` (
  `id` varchar(50) NOT NULL,
  `version` int default 1 not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `plane` (
  `name` varchar(50) DEFAULT NULL,
  `numberplate` varchar(50) DEFAULT NULL,
  `id` varchar(50) NOT NULL,
  `aircrafttype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_okt3vwgmoby1jsew888uiixpq` (`aircrafttype`),
  CONSTRAINT `FK_e4w9wuowwylouucireu4otyh0` FOREIGN KEY (`id`) REFERENCES `reservation_item` (`id`),
  CONSTRAINT `FK_okt3vwgmoby1jsew888uiixpq` FOREIGN KEY (`aircrafttype`) REFERENCES `aircrafttype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `reservation` (
  `id` varchar(50) NOT NULL,
  `version` int default 1 not null,
  `end` datetime DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `item_id` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_d9ekxagw3ervl6mwpklixuxc3` (`item_id`),
  KEY `FK_recqnfjcp370rygd9hjjxjtg` (`user_id`),
  CONSTRAINT `FK_d9ekxagw3ervl6mwpklixuxc3` FOREIGN KEY (`item_id`) REFERENCES `reservation_item` (`id`),
  CONSTRAINT `FK_recqnfjcp370rygd9hjjxjtg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role` (
  `id` varchar(50) NOT NULL,
  `rolename` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_to_role_mapping` (
  `id` varchar(50) NOT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gkq1lls31afb1efpx2t09ny61` (`role_id`),
  KEY `FK_sb71rxr1c8x0lqn7i9c56oqy` (`user_id`),
  CONSTRAINT `FK_gkq1lls31afb1efpx2t09ny61` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_sb71rxr1c8x0lqn7i9c56oqy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
