package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.implementations.ProjectManagementImp;

public class TeamPageController {

    @FXML
    private ListView teamList;


    public void setTeamList(Event mouseEvent) {

        ObservableList<TeamMember> members = FXCollections.observableArrayList(ProjectManagementImp.getProject().getTeamMembers());
        teamList.setItems(members);

    }
}