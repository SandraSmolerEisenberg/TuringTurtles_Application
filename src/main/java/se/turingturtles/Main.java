package se.turingturtles;

//Commented out imports are for the GUI later on. /Liv

import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.eventEventHandler;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//Font import
import javafx.scene.text.Font;
import se.turingturtles.controllers.ProjectManagementController;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;

public class Main extends Application {


    private static ProjectFactory factory = new ProjectFactory();

    @Override
    public void start(Stage window) throws IOException {
        //Application window size.
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.9;
        double height = screenSize.getHeight() * 0.8;
        window.setResizable(true);

        //Scene creation.
        Scene scene = new Scene(factory.loadFXML("projectmaster"), width, height);
        scene.getStylesheets().addAll();
        window.setScene(scene);
       // scene.getStylesheets().add(getClass().getResource("/se/turingturtles/css/03-projectoverview.css").toExternalForm());

        //Application window title.
        window.setTitle("Turing Turtles");
        Image image = factory.loadImage("turtle");
        window.getIcons().add(image);
        window.show();

        //Application fonts.
        //Font.loadFont(getStylesheets().add(getClass().getResource("https://fonts.googleapis.com/css?family=Lobster|Open+Sans+Condensed:300,300i,700|Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i&display=swap&subset=latin-ext")));
    }


    public static void main(String[] args) throws IOException {
        testData();
        launch();
    }

    private static void testData() {
            ProjectFactory factory = new ProjectFactory();
            ProjectManagement projectManagement;
            projectManagement = factory.makeProjectManagement();
            LocalDate localDate = LocalDate.now();
           projectManagement.createProject("TestProject", 10000.0, localDate.parse("2019-10-01"), localDate.parse("2020-01-20"));
            projectManagement.createTask("Task1", localDate.parse("2019-11-04"),localDate.parse("2019-11-11"));
            projectManagement.createTask("Task2", localDate.parse("2019-12-23"),localDate.parse("2019-12-30"));
            projectManagement.createTask("Task3", localDate.parse("2019-12-06"),localDate.parse("2020-01-10"));
            projectManagement.createTask("Task4", localDate.parse("2019-11-04"),localDate.parse("2019-11-11"));
            projectManagement.createTask("Task6", localDate.parse("2019-12-02"),localDate.parse("2019-12-09"));
            //projectManagement.createTask("Task7", 49,13);
            projectManagement.createMember("TeamMember1", 1, 1000);
            projectManagement.createMember("TeamMember2", 2, 2000);
            projectManagement.createRisk("Risk1", 1,4);
            projectManagement.createRisk("Risk2", 2,2);
            projectManagement.createRisk("Risk3", 6,2);
            projectManagement.createRisk("Risk4", 0,5);
            projectManagement.createRisk("Risk5", 3,3);
            projectManagement.createRisk("Risk6", 4,2);
            projectManagement.createRisk("Risk7", 5,10);
            projectManagement.createRisk("Risk8", 1,7);
            projectManagement.completeTask(ProjectManagementImp.getProject().getTasks().get(0)); //Complete a task
            ProjectManagementImp.getProject().getTeamMembers().get(0).addTask(ProjectManagementImp.getProject().getTasks().get(0));
        ProjectManagementImp.getProject().getTeamMembers().get(1).addTask(ProjectManagementImp.getProject().getTasks().get(1));
        ProjectManagementImp.getProject().getTeamMembers().get(0).addTask(ProjectManagementImp.getProject().getTasks().get(2));
        ProjectManagementImp.getProject().getTeamMembers().get(0).addTask(ProjectManagementImp.getProject().getTasks().get(3));
        ProjectManagementImp.getProject().getTeamMembers().get(0).addTask(ProjectManagementImp.getProject().getTasks().get(4));
            //ProjectManagementImp.getProject().getTasks().get(0).addTeamMember(ProjectManagementImp.getProject().getTeamMembers().get(0));

        }
    }


