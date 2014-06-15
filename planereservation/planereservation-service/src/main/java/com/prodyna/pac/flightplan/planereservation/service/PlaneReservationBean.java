/**
 * 
 */
package com.prodyna.pac.flightplan.planereservation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.pac.flightplan.common.interceptor.AuthorizationInterceptor;
import com.prodyna.pac.flightplan.common.interceptor.AuthorizedRoles;
import com.prodyna.pac.flightplan.common.interceptor.Logging;
import com.prodyna.pac.flightplan.monitoring.MethodCallsMonitored;
import com.prodyna.pac.flightplan.planereservation.entity.PlaneReservation;
import com.prodyna.pac.flightplan.reservation.entity.Reservation;
import com.prodyna.pac.flightplan.reservation.entity.ReservationStatus;
import com.prodyna.pac.flightplan.reservation.service.ReservationService;
import com.prodyna.pac.flightplan.user.entity.Role;
import com.prodyna.pac.flightplan.utils.LocalDateConverter;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
@Stateless
@Logging
@MethodCallsMonitored
@Interceptors(AuthorizationInterceptor.class)
@AuthorizedRoles({ Role.ADMIN, Role.USER })
public class PlaneReservationBean implements PlaneReservationService {

    @Inject
    private EntityManager em;

    @Inject
    private ReservationService reservationService;

    @Override
    public PlaneReservation createReservation(PlaneReservation reservation) {
        Reservation res = fromPlaneReservation(reservation);
        res = reservationService.createReservation(res);

        reservation.setId(res.getId());
        reservation.setStatus(res.getStatus());

        return reservation;
    }

    @Override
    public PlaneReservation updateReservation(PlaneReservation reservation) {
        Reservation res = fromPlaneReservation(reservation);
        res = reservationService.updateReservation(res);

        reservation.setId(res.getId());
        reservation.setStartTime(res.getStart());
        reservation.setEndTime(res.getEnd());
        reservation.setStatus(res.getStatus());
        return reservation;
    }

    @Override
    public void deleteReservationById(String reservationId) {
        reservationService.deleteReservationById(reservationId);
    }

    @Override
    @AuthorizedRoles({ Role.GUEST })
    public List<PlaneReservation> loadAllPlaneReservations() {
        Query query = em.createNamedQuery(PlaneReservation.QUERY_LOAD_ALL_PLANERESERVATIONS);

        @SuppressWarnings("unchecked")
        List<PlaneReservation> resultList = query.getResultList();
        return resultList;
    }

    @Override
    @AuthorizedRoles({ Role.GUEST })
    public List<PlaneReservation> loadPlaneReservationsByPlaneAndDate(String planeId, long dateMillis) {
        Query query = em.createNamedQuery(PlaneReservation.QUERY_LOAD_PLANERESERVATIONS_BY_PLANE_AND_DATE);
        LocalDateTime startOfDay = LocalDateConverter.dateToLocalDate(new Date(dateMillis)).atStartOfDay();
        LocalDateTime endOfDay = LocalDateConverter.dateToLocalDate(new Date(dateMillis)).atTime(23, 59, 59);
        Date startOfDayDate = LocalDateConverter.localDateTimeToDate(startOfDay);
        Date endOfDayDate = LocalDateConverter.localDateTimeToDate(endOfDay);

        List<ReservationStatus> statusList = new ArrayList<ReservationStatus>();
        statusList.add(ReservationStatus.RESERVED);
        statusList.add(ReservationStatus.LENT);

        query.setParameter("planeId", planeId);
        query.setParameter("dateStartOfDay", startOfDayDate);
        query.setParameter("dateEndOfDay", endOfDayDate);
        query.setParameter("dateStartOfDay2", startOfDayDate);
        query.setParameter("dateEndOfDay2", endOfDayDate);
        query.setParameter("statusList", statusList);

        @SuppressWarnings("unchecked")
        List<PlaneReservation> resultList = query.getResultList();
        return resultList;
    }

    @Override
    @AuthorizedRoles({ Role.GUEST })
    public List<PlaneReservation> loadPlaneReservationsByUserId(String userId) {
        StringBuilder jpql = new StringBuilder(PlaneReservation.QUERY_LOAD_PLANERESERVATIONS_BY_PILOTID);
        Query query = em.createNamedQuery(jpql.toString());
        query.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        List<PlaneReservation> resultList = query.getResultList();
        return resultList;
    }

    private Reservation fromPlaneReservation(PlaneReservation reservation) {
        Reservation res = new Reservation();
        res.setId(reservation.getId());
        res.setUser(reservation.getPilot());
        res.setItem(reservation.getPlane());
        res.setStart(reservation.getStartTime());
        res.setEnd(reservation.getEndTime());
        res.setStatus(reservation.getStatus());
        return res;
    }
}
