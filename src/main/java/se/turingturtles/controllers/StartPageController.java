package se.turingturtles.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import se.turingturtles.Main;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.streamIO.StreamJSON;

public class StartPageController {


    @FXML
    private Button createProjectButton;
    @FXML
    private Button loadProjectButton;


    ProjectFactory factory = new ProjectFactory();

    public void createNewProject(ActionEvent event) throws IOException {
        factory.changeScene(createProjectButton.getScene(),"createproject");
    }

    public void loadProject(ActionEvent event) throws Exception{
            StreamJSON testStream = factory.makeStream();
            testStream.importFromJSON();
            factory.changeScene(createProjectButton.getScene(),"projectmaster");

    }
}
