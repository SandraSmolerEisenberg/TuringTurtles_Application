package se.turingturtles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import se.turingturtles.streamIO.StreamIO;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main extends Application {

    private static ProjectFactory factory = new ProjectFactory();
    private static StreamIO streamIO = factory.makeStream();

    @Override
    public void start(Stage window) throws IOException {
        //Application window size.
        //Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //double width = screenSize.getWidth() * 0.6;
        //double height = screenSize.getHeight() * 0.8;
        window.setResizable(true);

        //Scene creation.
        Scene scene = new Scene(factory.loadFXML("startpage"), 800 , 600);
        scene.getStylesheets().addAll();
        window.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/se/turingturtles/css/00-default.css").toExternalForm());

        //Application window title.
        window.setTitle("Turing Turtle");
        Image image = factory.loadImage("turtle");
        window.getIcons().add(image);
        window.show();
    }

    public static void main(String[] args) {
        try {
            launch();
            streamIO.exportToStreamIO();
        }catch (Exception e){
            e.printStackTrace();
            streamIO.exportToStreamIO();
        }

    }
}


