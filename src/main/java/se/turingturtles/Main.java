package se.turingturtles;

//Commented out imports are for the GUI later on. /Liv

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import se.turingturtles.streamIO.StreamJSON;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;

public class Main extends Application {

    private static ProjectFactory factory = new ProjectFactory();
    private static StreamJSON json = factory.makeStream();

    @Override
    public void start(Stage window) throws IOException {
        //Application window size.
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.9;
        double height = screenSize.getHeight() * 0.8;
        window.setResizable(true);

        //Scene creation.
        Scene scene = new Scene(factory.loadFXML("startpage"), width, height);
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
        }catch (Exception e){
            json.exportToJSON();
        }

    }
}


