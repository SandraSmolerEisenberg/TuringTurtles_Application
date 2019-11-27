package se.turingturtles.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.implementations.ProjectFactory;

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
    public void createNewProject(ActionEvent event) {
       String budget = projectBudget.getText();
       String duration = projectDuration.getText();
       String name = projectName.getText();
       Validator validator = factory.makeValidator();
       if (validator.validateNumericInput(budget) && validator.validateNumericInput(duration) && validator.validateTextInput(name) ){
           ProjectManagement projectManagement= factory.makeProjectManagement();
           projectManagement.createProject(name , Double.parseDouble(budget) , Integer.parseInt(duration));
           projectBudget.clear();
           projectDuration.clear();
           projectName.clear();
       }
       else {
           if (!validator.validateNumericInput(budget)){
               projectBudget.clear();
               projectBudget.setPromptText("Invalid Budget!");
               //Color to be changed
               projectBudget.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

           }
           if (!validator.validateNumericInput(duration)){
               projectDuration.clear();
               projectDuration.setPromptText("Invalid Duration!");
               //Color to be changed
               projectDuration.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

           } if (!validator.validateTextInput(name)){
               projectName.clear();
               projectName.setPromptText("Invalid Name!");
               //Color to be changed
               projectName.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

           }
       }

    }

}
