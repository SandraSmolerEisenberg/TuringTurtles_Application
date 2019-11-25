package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.awt.event.MouseEvent;

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
    protected Button returnTeamPage;

    public void loadTeamList() {

        ObservableList<TeamMember> members = FXCollections.observableArrayList(ProjectManagementImp.getProject().getTeamMembers());
        teamList.setItems(members);

    }
    public void createNewMember(ActionEvent event){
        newMemberPage.setVisible(true);
        String name = enterName.getText();
        String id = enterID.getText();
        String hourlyWage = enterWage.getText();
        ProjectFactory factory = new ProjectFactory();
        Validator validator = factory.makeValidator();

        if (validator.validateTextInput(name) && validator.validateNumericInput(id) && validator.validateNumericInput(hourlyWage)) {
            ProjectManagement projectManagement = factory.createProjectManagement();
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

    public void returnToTeamPage() {
        newMemberPage.setVisible(false);
    }
}