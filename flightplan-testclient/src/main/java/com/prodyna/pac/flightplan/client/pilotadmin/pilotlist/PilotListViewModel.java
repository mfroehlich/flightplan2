/**
 * 
 */
package com.prodyna.pac.flightplan.client.pilotadmin.pilotlist;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.prodyna.pac.flightplan.client.model.PilotModel;
import com.prodyna.pac.flightplan.client.service.PilotClientService;
import com.prodyna.pac.flightplan.pilot.entity.Pilot;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PilotListViewModel {

    private final PilotClientService pilotClientService;

    public PilotListViewModel() {
        this.pilotClientService = new PilotClientService();
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ObservableList<PilotModel> loadPilotList() {
        ObservableList<PilotModel> pilotList = FXCollections.observableArrayList();
        List<Pilot> loadAllPilots = pilotClientService.loadAllPilots();
        loadAllPilots.forEach(pilot -> pilotList.add(new PilotModel(pilot)));
        return pilotList;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param pilotId
     */
    public void deletePilotById(String pilotId) {
        pilotClientService.deletePilotById(pilotId);
    }
}
