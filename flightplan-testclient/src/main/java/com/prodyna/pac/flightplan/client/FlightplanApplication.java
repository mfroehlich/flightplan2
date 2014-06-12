/**
 * 
 */
package com.prodyna.pac.flightplan.client;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.pac.flightplan.client.mainpage.MainPageView;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class FlightplanApplication extends Application {

    public static final Logger LOGGER = LoggerFactory.getLogger(FlightplanApplication.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        URL log4jXML = getClass().getClassLoader().getResource("logging/log4j.xml");
        DOMConfigurator.configure(log4jXML);

        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            new ExceptionHandler().handleException(throwable);
        });

        MainPageView mainPageView = new MainPageView();
        Scene scene = new Scene(mainPageView.getView());
        stage.setScene(scene);
        stage.show();
    }
}
