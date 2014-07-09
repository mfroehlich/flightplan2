/**
 * 
 */
package com.prodyna.pac.flightplan.client.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class Factory {

    public static GridPane createGridPane() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        return grid;
    }

    public static Button createButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button();
        button.setText(text);
        button.setOnAction(handler);

        return button;
    }

    public static Text createText(String textStr, String font) {
        Text text = new Text(textStr);
        text.setFont(Font.font(font, FontWeight.NORMAL, 20));

        return text;
    }

    /**
     * TODO mfroehlich Comment me
     * 
     * @param string
     * @return
     */
    public static Label createLabel(String string) {
        Label label = new Label(string);

        return label;
    }

    public static TextField createTextField(String text) {
        TextField tf = new TextField();
        tf.setText(text);

        return tf;
    }

    public static PasswordField createPasswordField() {
        PasswordField passwordField = new PasswordField();

        return passwordField;
    }
}
