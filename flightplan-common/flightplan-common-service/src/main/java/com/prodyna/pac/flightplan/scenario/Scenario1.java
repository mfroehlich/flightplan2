/**
 * 
 */
package com.prodyna.pac.flightplan.scenario;

public class Scenario1 implements Scenario {
    @Override
    public String[] getSqlStatements() {
        return new String[] {

                "DELETE FROM `reservation`",
                "DELETE FROM `plane`",
                "DELETE FROM `reservation_item`",

                "DELETE FROM `pilot_to_aircraft_type`",
                "DELETE FROM `pilot`",
                "DELETE FROM `aircraft_type`",

                "DELETE FROM `user_to_role_mapping`",
                "DELETE FROM `user`",
                "DELETE FROM `role`",

                "insert into role (id, role_name) values ('1', 'ADMIN')",
                "insert into role (id, role_name) values ('2 ', 'USER')",

                "insert into user (id, email, first_name, last_name, password, user_name) values ('1', 'mfroehlich@prodyna.com', 'Markus', 'Fröhlich', 'CY9rzUYh03PK3k6DJie09g==', 'mfroehlich')",
                "insert into user_to_role_mapping (id, user_id, role_id) values ('1', '1', '2')",
                "insert into pilot (id, licence_validity) values ('1', date('2016-05-21'))",

                "insert into aircraft_type (id, description) values ('1', 'Type 1')",
                "insert into aircraft_type (id, description) values ('2', 'Type 2')",
                "insert into aircraft_type (id, description) values ('1000', 'Not referenced AircraftType.')",

                "insert into pilot_to_aircraft_type (pilot_id, aircraft_type_id) values ('1', '1')",

                "insert into reservation_item (id) values ('1')",
                "insert into reservation_item (id) values ('2')",
                "insert into reservation_item (id) values ('3')",

                "insert into plane (id, name, number_plate, aircraft_type) values ('1', 'Plane 1', 'PLA-NE-1', '1')",
                "insert into plane (id, name, number_plate, aircraft_type) values ('2', 'Plane 2', 'PLA-NE-2', '1')",
                "insert into plane (id, name, number_plate, aircraft_type) values ('3', 'Plane 3', 'PLA-NE-3', '2')",

                "insert into reservation (id, item_id, user_id, start, end, status) values ('1', '1', '1', timestamp('2014-07-04 09:00:00'), timestamp('2014-07-04 10:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('2', '1', '1', timestamp('2014-07-04 11:00:00'), timestamp('2014-07-04 12:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('3', '1', '1', timestamp('2014-07-04 13:00:00'), timestamp('2014-07-04 14:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('4', '2', '1', timestamp('2014-07-04 15:00:00'), timestamp('2014-07-04 16:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('5', '2', '1', timestamp('2014-07-04 17:00:00'), timestamp('2014-07-04 18:00:00'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('6', '2', '1', timestamp('2014-07-04 19:00:00'), timestamp('2014-07-04 20:00:00'), 'RESERVED')" };
    }
}
