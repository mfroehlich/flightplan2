DROP SCHEMA IF EXISTS flightplan;
CREATE SCHEMA flightplan;
USE flightplan;

CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `version` int default 1 not null,
  `email` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT uc_user_username_unique UNIQUE (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `pilot` (
  `id` varchar(50) NOT NULL,
  `licence_validity` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_kp4l415fkxg688bg286x3cga5` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `aircraft_type` (
  `id` varchar(50) NOT NULL,
  `version` int default 1 not null,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `pilot_to_aircraft_type` (
  `pilot_id` varchar(50) NOT NULL,
  `aircraft_type_id` varchar(50) NOT NULL,
  PRIMARY KEY (`pilot_id`,`aircraft_type_id`),
  KEY `FK_g56mhw9sj2f3r6eltff9whlk8` (`aircraft_type_id`),
  CONSTRAINT `FK_g56mhw9sj2f3r6eltff9whlk8` FOREIGN KEY (`aircraft_type_id`) REFERENCES `aircraft_type` (`id`),
  CONSTRAINT `FK_pd7yfmmhx2wxqfm0hec24qeaq` FOREIGN KEY (`pilot_id`) REFERENCES `pilot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `reservation_item` (
  `id` varchar(50) NOT NULL,
  `version` int default 1 not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `plane` (
  `name` varchar(50) DEFAULT NULL,
  `number_plate` varchar(50) DEFAULT NULL,
  `id` varchar(50) NOT NULL,
  `aircraft_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_okt3vwgmoby1jsew888uiixpq` (`aircraft_type`),
  CONSTRAINT `FK_e4w9wuowwylouucireu4otyh0` FOREIGN KEY (`id`) REFERENCES `reservation_item` (`id`),
  CONSTRAINT `FK_okt3vwgmoby1jsew888uiixpq` FOREIGN KEY (`aircraft_type`) REFERENCES `aircraft_type` (`id`)
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
  `role_name` varchar(50) DEFAULT NULL,
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
  CONSTRAINT `FK_sb71rxr1c8x0lqn7i9c56oqy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- password: admin
insert into flightplan.user (id, email, first_name, last_name, password, user_name) values ( '1', 'admin@prodyna.com', 'Mr.', 'Administrator', 'ISMvKXpXpadDiUoOSoAfww==', 'admin');
-- password: test
insert into flightplan.user (id, email, first_name, last_name, password, user_name) values ( '2', 'mfroehlich@prodyna.com', 'Markus', 'Fröhlich', 'CY9rzUYh03PK3k6DJie09g==', 'mfroehlich');

insert into flightplan.role (id, role_name) values ('1', 'ADMIN');
insert into flightplan.role (id, role_name) values ('2', 'USER');

insert into flightplan.user_to_role_mapping (id, user_id, role_id) values ('1', '1', '1');
insert into flightplan.user_to_role_mapping (id, user_id, role_id) values ('2', '2', '2');

insert into flightplan.pilot (id, licence_validity) values ('2', date('2016-05-21'));

insert into flightplan.aircraft_type (id, description) values ('1', 'Type 1');
insert into flightplan.aircraft_type (id, description) values ('2', 'Type 2');
insert into flightplan.aircraft_type (id, description) values ('3', 'Type 3');
insert into flightplan.aircraft_type (id, description) values ('4', 'Type 4');
insert into flightplan.aircraft_type (id, description) values ('5', 'Type 5');

insert into flightplan.pilot_to_aircraft_type (pilot_id, aircraft_type_id) values ('2', '1');

insert into flightplan.reservation_item (id) values ('1');
insert into flightplan.reservation_item (id) values ('2');
insert into flightplan.reservation_item (id) values ('3');

insert into flightplan.plane (id, name, number_plate, aircraft_type) values ('1', 'Plane 1', 'PLA-NE-1', '1');
insert into flightplan.plane (id, name, number_plate, aircraft_type) values ('2', 'Plane 2', 'PLA-NE-2', '1');
insert into flightplan.plane (id, name, number_plate, aircraft_type) values ('3', 'Plane 3', 'PLA-NE-3', '2');

insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('1', '1', '2', timestamp('2014-07-04 09:00:00'), timestamp('2014-07-04 10:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('2', '1', '2', timestamp('2014-07-04 11:00:00'), timestamp('2014-07-04 12:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('3', '1', '2', timestamp('2014-07-04 13:00:00'), timestamp('2014-07-04 14:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('4', '2', '2', timestamp('2014-07-04 15:00:00'), timestamp('2014-07-04 16:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('5', '2', '2', timestamp('2014-07-04 17:00:00'), timestamp('2014-07-04 18:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('6', '2', '2', timestamp('2014-07-04 19:00:00'), timestamp('2014-07-04 20:00:00'), 'RESERVED');
