
-- TODO flightplan.user -> username benötigt ein unique constraint!! 


insert into flightplan.user (id, email, firstname, lastname, password, username) values ( '1', 'mfroehlich@prodyna.com', 'Markus', 'Fröhlich', 'db', 'markus');
insert into flightplan.user (id, email, firstname, lastname, password, username) values ( '2', 'annikafroehlich1@gmx.de', 'Annika', 'Fröhlich', 'db', 'annika');
insert into flightplan.user (id, email, firstname, lastname, password, username) values ( '3', 'test@gmx.de', 'Test', 'User', 'db', 'test');

insert into flightplan.role (id, rolename) values ('1', 'ADMIN');
insert into flightplan.role (id, rolename) values ('2', 'USER');
insert into flightplan.role (id, rolename) values ('3', 'GUEST');

insert into flightplan.user_to_role_mapping (id, user_id, role_id) values ('1', '1', '1');
insert into flightplan.user_to_role_mapping (id, user_id, role_id) values ('2', '2', '2');
insert into flightplan.user_to_role_mapping (id, user_id, role_id) values ('3', '3', '3');

insert into flightplan.pilot (id, licence_validity) values ('1', date('2015-01-01'));
insert into flightplan.pilot (id, licence_validity) values ('2', date('2016-01-01'));

insert into flightplan.planetype (id, description) values ('1', 'Planetype 1');
insert into flightplan.planetype (id, description) values ('2', 'Planetype 2');
insert into flightplan.planetype (id, description) values ('3', 'Planetype 3');
insert into flightplan.planetype (id, description) values ('4', 'Planetype 4');
insert into flightplan.planetype (id, description) values ('5', 'Planetype 5');

insert into flightplan.pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('1', '1');
insert into flightplan.pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('1', '2');
insert into flightplan.pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('1', '3');
insert into flightplan.pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('2', '3');
insert into flightplan.pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('2', '4');

insert into flightplan.reservation_item (id) values ('1');
insert into flightplan.reservation_item (id) values ('2');
insert into flightplan.reservation_item (id) values ('3');

insert into flightplan.plane (id, name, numberplate, aircrafttype) values ('1', 'Markusflieger', 'KLE-MF-123', '1');
insert into flightplan.plane (id, name, numberplate, aircrafttype) values ('2', 'Markusflieger 2', 'KLE-MF-456', '1');
insert into flightplan.plane (id, name, numberplate, aircrafttype) values ('3', 'Annikaflieger', 'KLE-AF-666', '2');

insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('1', '1', '1', timestamp('2014-05-21 14:00:00'), timestamp('2014-05-21 15:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('2', '1', '1', timestamp('2014-05-21 16:00:00'), timestamp('2014-05-21 17:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('3', '1', '1', timestamp('2014-05-21 18:00:00'), timestamp('2014-05-21 19:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('4', '1', '1', timestamp('2014-05-22 10:00:00'), timestamp('2014-05-22 11:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('5', '1', '1', timestamp('2014-05-22 12:00:00'), timestamp('2014-05-22 13:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('6', '1', '1', timestamp('2014-05-22 14:00:00'), timestamp('2014-05-22 15:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('7', '1', '2', timestamp('2014-05-22 16:00:00'), timestamp('2014-05-22 17:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('8', '1', '2', timestamp('2014-05-22 18:00:00'), timestamp('2014-05-22 19:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('9', '1', '2', timestamp('2014-05-22 20:00:00'), timestamp('2014-05-22 21:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('10', '2', '2', timestamp('2014-05-23 14:00:00'), timestamp('2014-05-23 15:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('11', '2', '1', timestamp('2014-05-23 15:00:00'), timestamp('2014-05-23 16:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('12', '2', '2', timestamp('2014-05-23 16:00:00'), timestamp('2014-05-23 17:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('13', '2', '1', timestamp('2014-05-23 17:00:00'), timestamp('2014-05-23 18:00:00'), 'RESERVED');
insert into flightplan.reservation (id, item_id, user_id, start, end, status) values ('14', '2', '2', timestamp('2014-05-23 18:00:00'), timestamp('2014-05-23 19:00:00'), 'RESERVED');

-- select * from flightplan.user;
-- select * from flightplan.role;
-- select * from flightplan.user_to_role_mapping;

-- update flightplan.user set username = "markus" where id = '1';
-- update flightplan.user set username = "annika" where id = '2';

-- update flightplan.user set password = md5("db") where id = '1';
-- update flightplan.user set password = sha2('db', 256) where id = '1';
-- update flightplan.user set password = "db" where id = '1';
-- update flightplan.user set password = "db" where id = '2';