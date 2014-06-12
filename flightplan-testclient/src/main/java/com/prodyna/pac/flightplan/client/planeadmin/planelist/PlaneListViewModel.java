/**
 * 
 */
package com.prodyna.pac.flightplan.client.planeadmin.planelist;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.prodyna.pac.flightplan.client.model.PlaneModel;
import com.prodyna.pac.flightplan.client.service.PlaneClientService;
import com.prodyna.pac.flightplan.plane.entity.Plane;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class PlaneListViewModel {

    private final PlaneClientService planeClientService;

    public PlaneListViewModel() {
        this.planeClientService = new PlaneClientService();
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @return
     */
    public ObservableList<PlaneModel> loadPlaneList() {
        ObservableList<PlaneModel> planeList = FXCollections.observableArrayList();
        List<Plane> loadAllPlanes = planeClientService.loadAllPlanes();
        loadAllPlanes.forEach(plane -> planeList.add(new PlaneModel(plane)));
        return planeList;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param planeId
     */
    public void deletePlaneById(String planeId) {
        planeClientService.deletePlaneById(planeId);
    }
}
