package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.Project;
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
    public Button createMemberButton;
    @FXML
    public Button refresh;

    public void loadTeamList(ActionEvent loadEvent) {

        ObservableList<TeamMember> members = FXCollections.observableArrayList(ProjectManagementImp.getProject().getTeamMembers());
        teamList.setItems(members);

    }
    public void createNewMember(ActionEvent event){
        ProjectFactory factory = new ProjectFactory();
        ProjectManagement projectManagement = factory.createProjectManagement();
        projectManagement.createMember(enterName.getText(), Integer.parseInt(enterID.getText()),Double.parseDouble(enterWage.getText()));
        enterName.clear();
        enterWage.clear();
        enterID.clear();
    }
}