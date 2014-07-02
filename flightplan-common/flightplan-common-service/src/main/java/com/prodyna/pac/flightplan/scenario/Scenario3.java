/**
 * 
 */
package com.prodyna.pac.flightplan.scenario;

public class Scenario3 implements Scenario {
    @Override
    public String[] getSqlStatements() {
        return new String[] {

                "DELETE FROM `reservation`",
                "DELETE FROM `plane`",
                "DELETE FROM `reservation_item`",

                "DELETE FROM `pilot_to_aircrafttype`",
                "DELETE FROM `pilot`",
                "DELETE FROM `aircrafttype`",

                "DELETE FROM `user_to_role_mapping`",
                "DELETE FROM `user`",
                "DELETE FROM `role`",

                "insert into role (id, rolename) values ('1', 'ADMIN')",
                "insert into role (id, rolename) values ('2 ', 'USER')",

                "insert into user (id, email, firstname, lastname, password, username) values ('1', 'user1@prodyna.com', 'Markus1', 'Fröhlich1', 'CY9rzUYh03PK3k6DJie09g==', 'user1')",
                "insert into user (id, email, firstname, lastname, password, username) values ('2', 'user2@prodyna.com', 'Markus2', 'Fröhlich2', 'CY9rzUYh03PK3k6DJie09g==', 'user2')",
                "insert into user (id, email, firstname, lastname, password, username) values ('3', 'user3@prodyna.com', 'Markus3', 'Fröhlich3', 'CY9rzUYh03PK3k6DJie09g==', 'user3')",

                "insert into user_to_role_mapping (id, user_id, role_id) values ('1', '1', '2')",
                "insert into user_to_role_mapping (id, user_id, role_id) values ('2', '2', '2')",
                "insert into user_to_role_mapping (id, user_id, role_id) values ('3', '3', '2')",

                "insert into pilot (id, licence_validity) values ('1', date('2016-05-21'))",
                "insert into pilot (id, licence_validity) values ('2', date('2016-05-21'))",
                "insert into pilot (id, licence_validity) values ('3', date('2016-05-21'))",

                "insert into aircrafttype (id, description) values ('1', 'Type 1')",
                "insert into aircrafttype (id, description) values ('2', 'Type 2')",

                "insert into pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('1', '1')",
                "insert into pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('2', '1')",
                "insert into pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('3', '1')",

                "insert into reservation_item (id) values ('1')",
                "insert into reservation_item (id) values ('2')",
                "insert into reservation_item (id) values ('3')",

                "insert into plane (id, name, numberplate, aircrafttype) values ('1', 'Plane 1', 'PLA-NE-1', '1')",
                "insert into plane (id, name, numberplate, aircrafttype) values ('2', 'Plane 2', 'PLA-NE-2', '1')",
                "insert into plane (id, name, numberplate, aircrafttype) values ('3', 'Plane 3', 'PLA-NE-3', '1')",

                "insert into reservation (id, item_id, user_id, start, end, status) values ('1', '1', '1', timestamp('2014-07-04 09:00:00'), timestamp('2014-07-04 10:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('2', '1', '1', timestamp('2014-07-04 11:00:00'), timestamp('2014-07-04 12:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('3', '1', '1', timestamp('2014-07-04 13:00:00'), timestamp('2014-07-04 14:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('4', '2', '1', timestamp('2014-07-04 15:00:00'), timestamp('2014-07-04 16:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('5', '2', '1', timestamp('2014-07-04 17:00:00'), timestamp('2014-07-04 18:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('6', '2', '1', timestamp('2014-07-04 19:00:00'), timestamp('2014-07-04 20:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('7', '1', '2', timestamp('2014-07-05 09:00:00'), timestamp('2014-07-05 10:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('8', '1', '2', timestamp('2014-07-05 11:00:00'), timestamp('2014-07-05 12:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('9', '1', '2', timestamp('2014-07-05 13:00:00'), timestamp('2014-07-05 14:00:00'), 'LENT')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('10', '2', '3', timestamp('2014-07-05 15:00:00'), timestamp('2014-07-05 16:00:00'), 'LENT')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('11', '2', '3', timestamp('2014-07-05 17:00:00'), timestamp('2014-07-05 18:00:00'), 'CANCELLED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('12', '2', '3', timestamp('2014-07-05 19:00:00'), timestamp('2014-07-05 20:00:00'), 'CANCELLED')" };
    }
}
