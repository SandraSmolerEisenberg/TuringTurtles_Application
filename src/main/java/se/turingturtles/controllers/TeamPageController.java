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


    ProjectFactory factory = new ProjectFactory();
    ProjectManagement projectManagement = factory.makeProjectManagement();

    public void loadTeamList() {
        ObservableList<TeamMember> members = FXCollections.observableArrayList(projectManagement.getTeamMembers());
        teamList.setItems(members);

    }
    public void deleteAlert(Event event){
        int temp = teamList.getSelectionModel().getSelectedIndex();
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Warning: Deleting member");
        deleteAlert.setHeaderText("WARNING!");
        deleteAlert.setContentText("You have selected to delete the following member: \nName: " + projectManagement.findTeamMember(temp).getName() + "\nID: " + projectManagement.findTeamMember(temp).getId() + "\n\nPlease click OK, in order to proceed!");
        deleteAlert.showAndWait();
        if(deleteAlert.getResult() == ButtonType.OK){
            projectManagement.removeMember(projectManagement.findTeamMember(temp));
            loadTeamList();
            memberInfoPage.setVisible(false);
        }
    }

    public void showNewMemberCreation(ActionEvent e) {
        newMemberPage.setVisible(true);
    }

    public void createNewMember(ActionEvent event){
        String name = enterName.getText();
        String id = enterID.getText();
        String hourlyWage = enterWage.getText();
        Validator validator = factory.makeValidator();


        if (validator.validateTextInput(name) && validator.validateNumericInput(id) && validator.validateNumericInput(hourlyWage)) {
            projectManagement.createMember(name, Integer.parseInt(id),Double.parseDouble(hourlyWage));
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
            if (!(name.equals("") && id.equals("") && hourlyWage.equals(""))) {
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
    }
    public void loadMemberInfoPage(Event e){
        memberInfoPage.setVisible(true);
        int temp = teamList.getSelectionModel().getSelectedIndex();
        ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveMemberTasks(projectManagement.findTeamMember(temp)));
        taskList.setItems(tasks);
        nameText.setText(projectManagement.findTeamMember(temp).getName());
        idText.setText(String.valueOf(projectManagement.findTeamMember(temp).getId()));
        wageText.setText(String.valueOf(projectManagement.findTeamMember(temp).getHourlyWage()));
    }
    /*public void searchTeamMember(){
        int idSearch = Integer.parseInt(searchBar.getText());

        for(int i = 0; i<ProjectManagementImp.getProject().getTeamMembers().size(); i++){
            int idCheck = ProjectManagementImp.getProject().getTeamMembers().get(i).getId();
            if(idCheck == idSearch){

            }
        }


    }*/
    public void returnToTeamPage() {
        memberInfoPage.setVisible(false);
        newMemberPage.setVisible(false);

        //We clear the fields in case of incorrect values entered the last time, so that the previous values will not appear again.
        enterName.clear();
        enterWage.clear();
        enterID.clear();

        /*We set the name and the background color of the fields to the default ones, in case of invalid user input data, so they will not
        appear the next time again. */
        enterName.setPromptText("Enter name");
        enterName.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        enterID.setPromptText("Enter ID");
        enterID.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        enterWage.setPromptText("Enter hourly wage");
        enterWage.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}