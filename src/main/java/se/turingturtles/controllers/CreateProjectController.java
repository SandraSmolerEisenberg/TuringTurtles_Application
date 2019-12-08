package se.turingturtles.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.implementations.ProjectFactory;

import java.io.IOException;
import java.time.LocalDate;

public class CreateProjectController {

    @FXML
    private Button startPageButton;
    @FXML
    private Button createNewProjectButton;
    @FXML
    private TextField projectName;
    @FXML
    private DatePicker projectStart;
    @FXML
    private DatePicker projectEnd;
    @FXML
    private TextField projectBudget;
    @FXML
    private RadioButton disclaimerButton;


    @FXML
    public void initialize(){
        createNewProjectButton.setDisable(true);
    }


    private ProjectFactory factory = new ProjectFactory();
    //Create project button set to disable by default
    public void activateCreateProjectButton(ActionEvent event){
        createNewProjectButton.setDisable(false);
    }

    public void backToStartPage(ActionEvent actionEvent) throws IOException {

        factory.changeScene(startPageButton.getScene(),"startPage");
    }
    //Validation of user input and creation a project
    public void createNewProject(ActionEvent event) throws IOException {
       String budget = projectBudget.getText();
       LocalDate projectStartDate = projectStart.getValue();
       LocalDate projectEndDate = projectEnd.getValue();
       String name = projectName.getText();
       Validator validator = factory.makeValidator();
       if (validator.validateNumericInput(budget) && validator.validateDate(projectStartDate, projectEndDate) && validator.validateTextInput(name) ){
           ProjectManagement projectManagement= factory.makeProjectManagement();
           projectManagement.createProject(name , Double.parseDouble(budget) , projectStartDate, projectEndDate);
           projectBudget.clear();
           projectName.clear();
           factory.changeScene(startPageButton.getScene(),"projectmaster");
       }
       else {
           if (!validator.validateNumericInput(budget)){
               projectBudget.clear();
               projectBudget.setPromptText("Invalid Budget!");


           }

           } if (!validator.validateTextInput(name)){
               projectName.clear();
               projectName.setPromptText("Invalid Name!");


           }
             if(!validator.validateDate(projectStartDate, projectEndDate)){
                 projectStart.getEditor().clear();
                 projectEnd.getEditor().clear();
                 projectStart.setPromptText("Set Valid Date!");
                 projectEnd.setPromptText("Set Valid Date!");

        }
       }

    }


