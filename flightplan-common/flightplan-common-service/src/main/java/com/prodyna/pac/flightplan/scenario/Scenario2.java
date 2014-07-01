/**
 * 
 */
package com.prodyna.pac.flightplan.scenario;

import java.time.LocalDateTime;

public class Scenario2 implements Scenario {

    private final LocalDateTime pastStartTime = LocalDateTime.now().minusMinutes(10);
    private final LocalDateTime pastEndTime = LocalDateTime.now().minusMinutes(2);

    private final LocalDateTime currentStartTime = LocalDateTime.now().minusMinutes(2);
    private final LocalDateTime currentEndTime = LocalDateTime.now().plusMinutes(10);

    private final LocalDateTime futureStartTime = LocalDateTime.now().plusMinutes(11);
    private final LocalDateTime futureEndTime = LocalDateTime.now().plusMinutes(20);

    private String dateTimeString(LocalDateTime time) {
        return time.getYear() + "-" + time.getMonthValue() + "-" + time.getDayOfMonth() + " " + time.getHour() + ":"
                + time.getMinute() + ":" + time.getSecond();
    }

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

                "insert into user (id, email, firstname, lastname, password, username) values ('1', 'mfroehlich@prodyna.com', 'Markus', 'Fröhlich', 'CY9rzUYh03PK3k6DJie09g==', 'mfroehlich')",
                "insert into user_to_role_mapping (id, user_id, role_id) values ('1', '1', '2')",
                "insert into pilot (id, licence_validity) values ('1', date('2016-05-21'))",

                "insert into user (id, email, firstname, lastname, password, username) values ('2', 'test@prodyna.com', 'test', 'test', 'CY9rzUYh03PK3k6DJie09g==', 'test')",
                "insert into user_to_role_mapping (id, user_id, role_id) values ('2', '2', '2')",
                "insert into pilot (id, licence_validity) values ('2', date('2016-05-21'))",

                "insert into aircrafttype (id, description) values ('1', 'Type 1')",
                "insert into aircrafttype (id, description) values ('2', 'Type 2')",

                "insert into pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('1', '1')",
                "insert into pilot_to_aircrafttype (pilot_id, aircrafttype_id) values ('2', '2')",

                "insert into reservation_item (id) values ('1')",
                "insert into reservation_item (id) values ('2')",
                "insert into reservation_item (id) values ('3')",

                "insert into plane (id, name, numberplate, aircrafttype) values ('1', 'Plane 1', 'PLA-NE-1', '1')",
                "insert into plane (id, name, numberplate, aircrafttype) values ('2', 'Plane 2', 'PLA-NE-2', '2')",
                "insert into plane (id, name, numberplate, aircrafttype) values ('3', 'Plane 3', 'PLA-NE-3', '2')",

                // NOTE Create reservations that took place in the past
                "insert into reservation (id, item_id, user_id, start, end, status) values ('pastReserved', '1', '1', timestamp('"
                        + dateTimeString(pastStartTime) + "'), timestamp('" + dateTimeString(pastEndTime)
                        + "'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('pastLent', '2', '2', timestamp('"
                        + dateTimeString(pastStartTime) + "'), timestamp('" + dateTimeString(pastEndTime)
                        + "'), 'LENT')",

                // NOTE Create a reservation that takes place at the moment
                "insert into reservation (id, item_id, user_id, start, end, status) values ('nowReserved', '1', '1', timestamp('"
                        + dateTimeString(currentStartTime) + "'), timestamp('" + dateTimeString(currentEndTime)
                        + "'), 'RESERVED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('nowCancelled', '1', '1', timestamp('"
                        + dateTimeString(currentStartTime) + "'), timestamp('" + dateTimeString(currentEndTime)
                        + "'), 'CANCELLED')",
                "insert into reservation (id, item_id, user_id, start, end, status) values ('nowLent', '2', '2', timestamp('"
                        + dateTimeString(currentStartTime) + "'), timestamp('" + dateTimeString(currentEndTime)
                        + "'), 'LENT')",

                // NOTE Create a reservation that takes place in the future
                "insert into reservation (id, item_id, user_id, start, end, status) values ('futureReserved', '1', '1', timestamp('"
                        + dateTimeString(futureStartTime) + "'), timestamp('" + dateTimeString(futureEndTime)
                        + "'), 'RESERVED')", };
    }
}
