package se.turingturtles;

//Commented out imports are for the GUI later on. /Liv

import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.eventEventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import se.turingturtles.implementations.ProjectCalculationsImp;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import se.turingturtles.streamIO.StreamJSON;

import java.awt.*;
import java.io.*;
import java.util.Calendar;

public class Main extends Application {

    private static ProjectFactory factory = new ProjectFactory();

    @Override
    public void start(Stage window) throws IOException {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.9;
        double height = screenSize.getHeight() * 0.8;
        window.setResizable(true);
        Scene scene = new Scene(factory.setFXML("teampage"), width, height);
        window.setScene(scene);
        window.setTitle("Turing Turtles");
        Image image = factory.loadImage("turtle");
        window.getIcons().add(image);
        window.show();
    }


    public static void main(String[] args) throws IOException {
        testData();
        launch();
    }

    private static void testData() {
            ProjectFactory factory = new ProjectFactory();
            ProjectManagement projectManagement;
            projectManagement = factory.createProjectManagement();
            projectManagement.createProject("TestProject", 10000, 23);
            projectManagement.createTask("Task1", 47,10);
            projectManagement.createTask("Task2", 47,20);
            projectManagement.createMember("TeamMember1", 1, 1000);
            projectManagement.createMember("TeamMember2", 2, 2000);
            projectManagement.createRisk("Risk1", 10,10);
            projectManagement.createRisk("Risk2", 20,20);

        }
    }


