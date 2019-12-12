package se.turingturtles.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import se.turingturtles.ProjectManagement;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.streamIO.StreamIO;

public class StartPageController {


    @FXML
    private Button createProjectButton;
    @FXML
    private Button loadProjectButton;


    ProjectFactory factory = new ProjectFactory();
    ProjectManagement projectManagement = factory.makeProjectManagement();

    public void createNewProject(ActionEvent event) throws IOException {
        factory.changeScene(createProjectButton.getScene(),"createproject");
    }

    public void loadProject(ActionEvent event) throws Exception{
            StreamIO testStream = factory.makeStream();
            testStream.importFromStreamIO();
            projectManagement.triggerCalculations();
            factory.changeScene(createProjectButton.getScene(),"projectmaster");

    }
}
