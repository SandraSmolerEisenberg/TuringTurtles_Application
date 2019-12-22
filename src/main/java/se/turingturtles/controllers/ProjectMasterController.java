package se.turingturtles.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import se.turingturtles.implementations.ProjectFactory;

import java.io.IOException;


public class ProjectMasterController {

    @FXML private AnchorPane projectMasterTabs;
    @FXML private TabPane masterTab;
    @FXML private AnchorPane overviewTab;
    @FXML private AnchorPane taskPage;
    @FXML private AnchorPane teamPage;
    @FXML private BorderPane riskPage;
    @FXML private AnchorPane projectManagementPage;
    @FXML private Tab projectOverviewTab;
    @FXML private Tab tasksTab;
    @FXML private Tab teamMembersTab;
    @FXML private Tab riskTab;
    @FXML private Tab projectManagementTab;
    @FXML private Button startPageButton;

    private ProjectFactory factory = new ProjectFactory();

    public void initialize(){
       projectMasterTabs.getStylesheets().add(getClass().getResource("/se/turingturtles/css/default.css").toExternalForm());

    }

    public void backToStartPage(ActionEvent actionEvent) throws IOException {
        factory.changeScene(startPageButton.getScene(),"startpage");
    }

}
