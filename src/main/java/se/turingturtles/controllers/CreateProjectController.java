package se.turingturtles.controllers;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.Project;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import javax.imageio.plugins.tiff.TIFFField;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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


    public void initialize(){
        createNewProjectButton.setDisable(true);
    }


    ProjectFactory factory = new ProjectFactory();

    public void activateCreateProjectButton(ActionEvent event){
        createNewProjectButton.setDisable(false);
    }

    public void backToStartPage(ActionEvent actionEvent) throws IOException {

        factory.changeScene(startPageButton.getScene(),"startPage");
    }

    public void createNewProject(ActionEvent event) {
       String budget = projectBudget.getText();
       String duration = projectDuration.getText();
       String name = projectName.getText();
       Validator validator = factory.makeValidator();
       if (validator.validateNumericInput(budget) && validator.validateNumericInput(duration) && validator.validateTextInput(name) ){
           ProjectManagement projectManagement= factory.createProjectManagement();
           projectManagement.createProject(name , Double.parseDouble(budget) , Integer.parseInt(duration));
           projectBudget.clear();
           projectDuration.clear();
           projectName.clear();
       }
       else {
           if (!validator.validateNumericInput(budget)){
               projectBudget.clear();
               projectBudget.setPromptText("Invalid Budget!");
               projectBudget.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

           }
           if (!validator.validateNumericInput(duration)){
               projectDuration.clear();
               projectDuration.setPromptText("Invalid Duration!");
               projectDuration.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

           } if (!validator.validateTextInput(name)){
               projectName.clear();
               projectName.setPromptText("Invalid Name!");
               projectName.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

           }
       }

    }

}
