package se.turingturtles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import se.turingturtles.streamIO.StreamIO;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class App extends Application {

    private static ProjectFactory factory = new ProjectFactory();
    private static StreamIO streamIO = factory.makeStream();

    @Override
    public void start(Stage window) throws IOException {
        //Application window size.
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //double width = screenSize.getWidth() * 0.5;
        double height = screenSize.getHeight() * 1;
        window.setResizable(true);

        //Scene creation.
        Scene scene = new Scene(factory.loadFXML("startpage"), 800 , height);
        scene.getStylesheets().addAll();
        window.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/se/turingturtles/css/default.css").toExternalForm());

        //Application window title.
        window.setTitle("Turing Turtle");
        Image image = factory.loadImage("turtle");
        window.getIcons().add(image);
        window.show();
    }

    public static void run() {
        try {
            launch();
            streamIO.exportToStreamIO();
        }catch (Exception e){
            Alert idTaken = new Alert(Alert.AlertType.ERROR);
            idTaken.setGraphic(factory.loadErrorNode());
            idTaken.setTitle("Error!");
            idTaken.setHeaderText("Internal error!");
            idTaken.setContentText("Please restart the application.");
            idTaken.showAndWait();
            streamIO.exportToStreamIO();
        }

    }
}


