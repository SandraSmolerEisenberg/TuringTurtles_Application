package se.turingturtles.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import se.turingturtles.application.ProjectManagement;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.streamIO.StreamIO;

public class StartPageController {


    @FXML private Button createProjectButton;
    @FXML private Button loadProjectButton;


    private ProjectFactory factory = new ProjectFactory();
    private ProjectManagement projectManagement = factory.makeProjectManagement();

    public void createNewProject(ActionEvent event) throws IOException {
        factory.changeScene(createProjectButton.getScene(),"createproject");
    }

    public void loadProject(ActionEvent event) {
            StreamIO testStream = factory.makeStream();
            try {
                testStream.importFromStreamIO();
                projectManagement.triggerCalculations();
                factory.changeScene(loadProjectButton.getScene(),"projectmaster");
            }
            catch (Exception e){
                Alert idTaken = new Alert(Alert.AlertType.ERROR);
                idTaken.setGraphic(factory.loadErrorNode());
                idTaken.setTitle("Error!");
                idTaken.setHeaderText("Fail to import data!");
                idTaken.setContentText("Internal data import error.");
                idTaken.showAndWait();
            }


    }
}
