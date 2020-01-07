package se.turingturtles.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import se.turingturtles.application.ProjectManagement;
import se.turingturtles.application.Validator;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.streamIO.StreamIO;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class CreateProjectController {



    @FXML private AnchorPane createProjectAnchor;
    @FXML private Button startPageButton;
    @FXML private Button createNewProjectButton;
    @FXML private TextField projectName;
    @FXML private DatePicker projectStart;
    @FXML private DatePicker projectEnd;
    @FXML private TextField projectBudget;
    @FXML private RadioButton disclaimerButton;

    private ProjectFactory factory = new ProjectFactory();
    private StreamIO json = factory.makeStream();


    @FXML
    public void initialize(){
        createNewProjectButton.setDisable(true);
        setDatePicker(projectStart, "StartDate");
        setDatePicker(projectEnd, "EndDate");
    }

    private void setDatePicker(DatePicker datePicker, String calendar){
        datePicker.setEditable(false);
        StringConverter<LocalDate> defaultConverter = datePicker.getConverter();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (calendar.equals("StartDate")) {
                    setDisable(empty || date.getDayOfWeek() != DayOfWeek.MONDAY);
                }
                else if (calendar.equals("EndDate")){
                    setDisable(empty || date.getDayOfWeek() != DayOfWeek.SUNDAY);
                }
            }
        });
        datePicker.setPromptText("Choose date:");
    }

    //Create project button set to disable by default
    public void activateCreateProjectButton(ActionEvent event){
        if (createNewProjectButton.isDisable()){
            createNewProjectButton.setDisable(false);}
        else{
            createNewProjectButton.setDisable(true);}

    }

    public void backToStartPage(ActionEvent actionEvent) throws IOException {

        factory.changeScene(startPageButton.getScene(),"startpage");
        createNewProjectButton.setDisable(true);
        disclaimerButton.setSelected(false);
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
           json.exportToStreamIO();
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


