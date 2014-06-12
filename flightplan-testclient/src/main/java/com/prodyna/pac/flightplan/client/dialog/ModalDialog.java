/**
 * 
 */
package com.prodyna.pac.flightplan.client.dialog;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ModalDialog extends Stage {

    public ModalDialog(Parent root) {
        Scene scene = new Scene(root);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }
}
