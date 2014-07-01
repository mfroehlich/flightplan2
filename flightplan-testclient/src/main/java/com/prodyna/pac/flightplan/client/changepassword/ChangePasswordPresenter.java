/**
 * 
 */
package com.prodyna.pac.flightplan.client.changepassword;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;

import com.prodyna.pac.flightplan.user.exception.UserValidationException;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ChangePasswordPresenter implements Initializable {

    @FXML
    private PasswordField oldPwd;

    @FXML
    private PasswordField newPwd1;

    @FXML
    private PasswordField newPwd2;

    private ChangePasswordViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel = new ChangePasswordViewModel();
        viewModel.oldPwdProperty().bindBidirectional(oldPwd.textProperty());
        viewModel.newPwd1Property().bindBidirectional(newPwd1.textProperty());
        viewModel.newPwd2Property().bindBidirectional(newPwd2.textProperty());
    }

    public void initItems(String userId) {
        viewModel.userIdProperty().set(userId);
    }

    public void changePassword(ActionEvent event) throws UserValidationException {
        viewModel.changePassword();

        /* Close the dialog. */
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
