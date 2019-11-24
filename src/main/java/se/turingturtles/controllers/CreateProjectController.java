package se.turingturtles.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.Project;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.io.IOException;

public class CreateProjectController {

    @FXML
    private Button startPageButton;
    @FXML
    private Button createNewProjectButton;
    @FXML
    private TextField projectName;
    @FXML
    private TextField projectDuration;
    @FXML
    private TextField projectBudget;

    ProjectFactory factory = new ProjectFactory();


    public void backToStartPage(ActionEvent actionEvent) throws IOException {

        factory.changeScene(startPageButton.getScene(),"startPage");
    }


    public void createNewProject(ActionEvent event) {
       double budget = Double.parseDouble( projectBudget.getText());
       int duration = Integer.parseInt(projectDuration.getText());
       String name = projectName.getText();
       ProjectManagement projectManagement= factory.createProjectManagement();
       projectManagement.createProject(name,budget,duration);
       projectBudget.clear();
       projectDuration.clear();
       projectName.clear();
       System.out.println(ProjectManagementImp.getProject().toString());
    }




}
