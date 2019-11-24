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
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import se.turingturtles.streamIO.StreamJSON;

import java.io.*;

public class Main extends Application {

    private static ProjectFactory factory = new ProjectFactory();
    private static Main main;
    private ProjectManagement projectManagement;

    @Override
    public void start(Stage window) throws IOException {
        ProjectFactory projectFactory = new ProjectFactory();
        Scene scene = new Scene(projectFactory.setFXML("startpage"));
        window.setScene(scene);
        window.setTitle("Turing Turtles");
        Image image = projectFactory.loadImage("turtle");
        window.getIcons().add(image);
        window.show();
    }


//    //Default method to change scenes(pages)
//    public static void changeScene(Scene scene, String fxml) throws IOException {
//
//        scene.setRoot(setFXML(fxml));
//    }

    public static void main(String[] args) throws IOException {
        main = new Main();
        main.test();
        StreamJSON testStream = factory.makeStream();

        launch();
    }

    private void test() {
        projectManagement = factory.createProjectManagement();
        System.out.println("-----Testing-----");
        System.out.println("Testing method Create Project: ");
        projectManagement.createProject("TestProject", 10000, 23);
        System.out.println("Result: "+ProjectManagementImp.getProject().toString());
        System.out.println("Testing method Create Task: ");
        projectManagement.createTask("Task1", 47,10);
        projectManagement.createTask("Task2", 47,20);
        System.out.println("Result: "+ProjectManagementImp.getProject().getTasks() + "Array size: " + ProjectManagementImp.getProject().getTasks().size());
        System.out.println("Testing method Create Team Member: ");
        projectManagement.createMember("TeamMember1", 1, 1000);
        projectManagement.createMember("TeamMember2", 2, 2000);
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers() + "Array size: " + ProjectManagementImp.getProject().getTeamMembers().size());
        System.out.println("Testing method Assign to Task: ");
        projectManagement.assignTask(ProjectManagementImp.getProject().getTeamMembers().get(0),ProjectManagementImp.getProject().getTasks().get(0));
        System.out.println("Result: " + ProjectManagementImp.getProject().getTasks().get(0).getTeamMembers() + "Team Member: " + ProjectManagementImp.getProject().getTasks().get(0).getTeamMembers().get(0).getTasks());
        System.out.println("Testing method Assign to Task: ");
        projectManagement.assignTask(ProjectManagementImp.getProject().getTeamMembers().get(1), ProjectManagementImp.getProject().getTasks().get(1));
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers().get(1).getTasks());
        System.out.println("Testing method retrieve member tasks: ");
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers().get(0).getTasks());
        System.out.println("Testing method retrieve Team members: ");
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers() + "Arrays size: " + ProjectManagementImp.getProject().getTeamMembers().size());
        System.out.println("Testing method find all team member: ");
        System.out.println("Result: " + projectManagement.findTeamMember(1));
        System.out.println("Testing method find Task: ");
        System.out.println("Result: " + projectManagement.findTask("Task1"));
        System.out.println("Testing method retrieve all tasks: ");
        System.out.println("Result: " + projectManagement.retrieveTasks());
        System.out.println("Testing method complete Task: ");
        System.out.println("Task status: " + ProjectManagementImp.getProject().getTasks().get(0).getCompletion());
        projectManagement.completeTask(ProjectManagementImp.getProject().getTasks().get(0));
        System.out.println("Result: " + ProjectManagementImp.getProject().getTasks().get(0).getCompletion() );
        System.out.println("Testing method remove Task: ");
        projectManagement.removeTask(ProjectManagementImp.getProject().getTasks().get(0));
        System.out.println("Result Project: " + ProjectManagementImp.getProject().getTasks().size() );
        System.out.println("Result Member: " + ProjectManagementImp.getProject().getTeamMembers().get(0).getTasks() );
        System.out.println("Testing method remove Member: ");
        projectManagement.removeMember(ProjectManagementImp.getProject().getTeamMembers().get(1));
        System.out.println("Result: " + ProjectManagementImp.getProject().getTeamMembers().size());
        System.out.println("Result Task: " + ProjectManagementImp.getProject().getTasks().get(0).getTeamMembers());
        System.out.println("Testing Time spent on project:");
        System.out.println(ProjectManagementImp.getProject().getStartWeek());
        System.out.println("Result: " + projectManagement.timeSpentOnProject());
        System.out.println("Testing method time spent by team member: ");
        System.out.println("Result: " + projectManagement.timeSpentByTeamMember(ProjectManagementImp.getProject().getTeamMembers().get(0)));
        System.out.println("Testing create Risk Method: ");
        projectManagement.createRisk("Risk1", 10,10);
        System.out.println("Result: " + ProjectManagementImp.getProject().getRisk().get(0) );
        projectManagement.createRisk("Risk2", 20,20);
        System.out.println("Testing retrieve all Risk: ");
        System.out.println("Result: " + projectManagement.retrieveRisk());

    }

}