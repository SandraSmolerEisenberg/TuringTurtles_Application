package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.Task;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

public class TaskPageController {

    @FXML
    private TableView taskTableView;

    @FXML
    private TableColumn taskName;

    @FXML
    private TableColumn taskStartWeek;

    @FXML
    private TableColumn taskDuration;

    @FXML
    private TableColumn taskTeamMembersAmount;

    @FXML
    private TableColumn taskStatus;

    @FXML
    private Button taskCreateTaskButton;

    @FXML
    private Text taskHeaderText;

    @FXML
    private AnchorPane createTaskAnchorPane;

    @FXML
    private TextField newTaskName;

    @FXML
    private TextField newTaskStartWeek;

    @FXML
    private TextField newTaskDuration;

    @FXML
    private Button newTaskCreateButton;

    private ProjectFactory projectFactory = new ProjectFactory();
    private ProjectManagement projectManagement = projectFactory.makeProjectManagement();

    @FXML public void initialize(){
        taskName.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskStartWeek.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
        taskDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        taskTeamMembersAmount.setCellValueFactory(new PropertyValueFactory<>("totalTeamMembers"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("completion"));
        ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveTasks());
        taskTableView.setItems(tasks);

        createTaskAnchorPane.setVisible(false);
    }

    @FXML public void createNewTask(ActionEvent event){
        if (taskTableView.isVisible()){
            taskTableView.setVisible(false);
            createTaskAnchorPane.setVisible(true);
            taskCreateTaskButton.setText("View Tasks");
        }
        else {
            createTaskAnchorPane.setVisible(false);
            taskTableView.setVisible(true);
            taskCreateTaskButton.setText("Create Task");
        }

    }
    @FXML public void applyNewTask(ActionEvent event){
        String name = newTaskName.getText();
        String duration = newTaskDuration.getText();
        String startWeek = newTaskStartWeek.getText();
        Validator validator = projectFactory.makeValidator();
        if (validator.validateNumericInput(duration) && validator.validateNumericInput(startWeek) && validator.validateTextInput(name) ){
            int newStartWeek = Integer.parseInt(startWeek);
            int newDuration = Integer.parseInt(duration);
            if (newStartWeek < ProjectManagementImp.getProject().getStartWeek()){
                newTaskStartWeek.clear();
                newTaskStartWeek.setPromptText("Start Week Can NOT be outside of the project duration!");
                //Color to be changed
                newTaskStartWeek.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

            }
            else if (newDuration > ProjectManagementImp.getProject().getDuration()){
                newTaskDuration.clear();
                newTaskDuration.setPromptText("Duration Can NOT be outside of the project duration!!");
                //Color to be changed
                newTaskDuration.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else {
                ProjectManagement projectManagement = projectFactory.makeProjectManagement();
                projectManagement.createTask(name, newStartWeek, newDuration);
                newTaskName.clear();
                newTaskStartWeek.clear();
                newTaskDuration.clear();
            }
        }
        else {
            if (!validator.validateNumericInput(startWeek)){
                newTaskStartWeek.clear();
                newTaskStartWeek.setPromptText("Invalid Start Week!");
                //Color to be changed
                newTaskStartWeek.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

            }
            if (!validator.validateNumericInput(duration)){
                newTaskDuration.clear();
                newTaskDuration.setPromptText("Invalid Duration!");
                //Color to be changed
                newTaskDuration.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

            } if (!validator.validateTextInput(name)){
                newTaskName.clear();
                newTaskName.setPromptText("Invalid Name!");
                //Color to be changed
                newTaskName.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

            }
        }

    }

}
