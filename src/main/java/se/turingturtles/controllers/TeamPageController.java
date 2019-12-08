package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

public class TeamPageController {

    public AnchorPane teamPage;
    @FXML
    private ListView teamList;
    @FXML
    private TextField enterName;
    @FXML
    private TextField enterID;
    @FXML
    private TextField enterWage;
    @FXML
    protected Button createMemberButton;
    @FXML
    private AnchorPane newMemberPage;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView taskList;
    @FXML
    private Text nameText;
    @FXML
    private Text idText;
    @FXML
    private Text wageText;
    @FXML
    private AnchorPane memberInfoPage;
    @FXML
    private AnchorPane memberEditPage;
    @FXML
    private TextField editName;
    @FXML
    private TextField editWage;
    @FXML
    private Text editMemberInfoText;
    @FXML
    private AnchorPane memberAssignTaskPage;
    @FXML
    private Text memberAssignTaskInfoText;
    @FXML
    private ListView assignTaskList;

    ProjectFactory factory = new ProjectFactory();
    ProjectManagement projectManagement = factory.makeProjectManagement();
    Validator validator = factory.makeValidator();
    private int lastViewedID;

    @FXML public void initialize(){
        loadTeamList();
    }

    public void loadTeamList() {
        ObservableList<TeamMember> members = FXCollections.observableArrayList(projectManagement.getTeamMembers());
        teamList.setItems(members);

    }

    public int findSelectedID() {
        Object selectedMember = teamList.getSelectionModel().getSelectedItem();
        int foundID = ((TeamMember) selectedMember).getId();
        return foundID;
    }

    public void deleteAlert(Event event) {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Warning: Deleting member");
        deleteAlert.setHeaderText("WARNING!");
        deleteAlert.setContentText("You have selected to delete the following member: \nName: " + projectManagement.findTeamMember(findSelectedID()).getName() + "\nID: " + projectManagement.findTeamMember(findSelectedID()).getId() + "\n\nPlease click OK, in order to proceed!");
        deleteAlert.showAndWait();
        if (deleteAlert.getResult() == ButtonType.OK) {
            projectManagement.removeMember(projectManagement.findTeamMember(findSelectedID()));
            loadTeamList();
            memberInfoPage.setVisible(false);
        }
    }

    public void showNewMemberCreation(ActionEvent e) {
        newMemberPage.setVisible(true);
        memberInfoPage.setVisible(false);
        memberEditPage.setVisible(false);
        memberAssignTaskPage.setVisible(false);
    }

    public void showEditMemberPage(ActionEvent e) {
        memberEditPage.setVisible(true);
        memberInfoPage.setVisible(false);
        editMemberInfoText.setText("You are editing: " + projectManagement.findTeamMember(lastViewedID).getName() + " with ID: " + lastViewedID);
    }

    public void editMember(ActionEvent event) {
        String name = editName.getText();
        String wage = editWage.getText();

        if (validator.validateTextInput(name) && validator.validateNumericInput(wage)) {
            projectManagement.findTeamMember(lastViewedID).setName(name);
            projectManagement.findTeamMember(lastViewedID).setHourlyWage(Double.parseDouble(wage));
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Success!");
            confirmationAlert.setHeaderText("Successfully edited team-member with ID: " + lastViewedID + ".");
            confirmationAlert.setContentText("The following information was edited: " + "\n" + "Name: " +  editName.getText() + "\n" + "Hourly wage: " + editWage.getText() + " SEK.");
            confirmationAlert.showAndWait();
            editName.clear();
            editWage.clear();
            returnToTeamPage();
            loadTeamList();
        } else {
            if (!validator.validateTextInput(name)) {
                editName.clear();
                editName.setPromptText("Invalid Name!");
                //Color to be changed
                editName.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            if (!validator.validateNumericInput(wage)) {
                editWage.clear();
                editWage.setPromptText("Invalid Wage!");
                //Color to be changed
                editWage.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }

    public void createNewMember(ActionEvent event) {
        String name = enterName.getText();
        String id = enterID.getText();
        String hourlyWage = enterWage.getText();

        if (validator.validateTextInput(name) && validator.validateNumericInput(id) && validator.validateNumericInput(hourlyWage)) {
            projectManagement.createMember(name, Integer.parseInt(id), Double.parseDouble(hourlyWage));
            enterName.clear();
            enterWage.clear();
            enterID.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New team member creation was successful!");
            alert.setHeaderText("You have created a new team member successfully!");
            alert.setContentText("A new team member has been created with the following information: \nName: " + name + "\nID: " + id + "\nHourly Wage: " + hourlyWage + "\n\nPlease click OK, in order to proceed!");
            alert.showAndWait();

            loadTeamList();
        } else {
            //With the above if statement we make sure that in case of empty values, the fields will not show the error messages.
            if (!validator.validateTextInput(name)) {
                enterName.clear();
                enterName.setPromptText("Invalid Name!");
                //Color to be changed
                enterName.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (!validator.validateNumericInput(id)) {
                enterID.clear();
                enterID.setPromptText("Invalid ID!");
                //Color to be changed
                enterID.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            if (!validator.validateNumericInput(hourlyWage)) {
                enterWage.clear();
                enterWage.setPromptText("Invalid Wage!");
                //Color to be changed
                enterWage.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }
    public void loadAssignTaskList(){
        ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveTasks());
        assignTaskList.setItems(tasks);
    }
    public void showMemberAssignTaskPage(Event event){
        memberInfoPage.setVisible(false);
        memberAssignTaskPage.setVisible(true);
        memberAssignTaskInfoText.setText("You are assigning tasks to: " + projectManagement.findTeamMember(lastViewedID).getName() + " with ID: " + lastViewedID);
        loadAssignTaskList();
    }
    public void assignTaskToMember(Event event){
        if(!projectManagement.findTeamMember(lastViewedID).getTasks().contains(assignTaskList.getSelectionModel().getSelectedItem())) {
            projectManagement.assignTask(projectManagement.findTeamMember(lastViewedID), (Task) assignTaskList.getSelectionModel().getSelectedItem());
            loadAssignTaskList();
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success!");
            successAlert.setHeaderText("Successful assignment!");
            successAlert.setContentText("You have added " + projectManagement.findTeamMember(lastViewedID).getName() + " to " + ((Task) assignTaskList.getSelectionModel().getSelectedItem()).getName() + ".");
            successAlert.showAndWait();
        }else{
            Alert assignmentError = new Alert(Alert.AlertType.ERROR);
            assignmentError.setTitle("Error!");
            assignmentError.setHeaderText("Assignment to task failed!");
            assignmentError.showAndWait();
        }
    }
    public void loadMemberInfoPage() {
        memberAssignTaskPage.setVisible(false);
        newMemberPage.setVisible(false);
        memberInfoPage.setVisible(true);
        memberEditPage.setVisible(false);
        ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveMemberTasks(projectManagement.findTeamMember(lastViewedID)));
        taskList.setItems(tasks);
        nameText.setText(projectManagement.findTeamMember(lastViewedID).getName());
        idText.setText(String.valueOf(projectManagement.findTeamMember(lastViewedID).getId()));
        wageText.setText(String.valueOf(projectManagement.findTeamMember(lastViewedID).getHourlyWage()));


    }

    public void selectTeamMember(Event e) {
        int selectedID = findSelectedID();
        lastViewedID = selectedID;
        loadMemberInfoPage();

    }

    public void searchTeamMember(Event e) {
        int idSearch = Integer.parseInt(searchBar.getText());
        lastViewedID = idSearch;
        loadMemberInfoPage();
        searchBar.clear();
        searchBar.setPromptText("Search by ID");
    }


    public void returnToTeamPage() {
        memberInfoPage.setVisible(false);
        newMemberPage.setVisible(false);
        memberEditPage.setVisible(false);
        memberAssignTaskPage.setVisible(false);
        //We clear the fields in case of incorrect values entered the last time, so that the previous values will not appear again.
        enterName.clear();
        enterWage.clear();
        enterID.clear();

        /*We set the name and the background color of the fields to the default ones, in case of invalid user input data, so they will not
        appear the next time again. */
        editName.setPromptText("Enter name");
        editName.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        editWage.setPromptText("Enter hourly wage");
        editWage.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        enterName.setPromptText("Enter name");
        enterName.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        enterID.setPromptText("Enter ID");
        enterID.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        enterWage.setPromptText("Enter hourly wage");
        enterWage.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}