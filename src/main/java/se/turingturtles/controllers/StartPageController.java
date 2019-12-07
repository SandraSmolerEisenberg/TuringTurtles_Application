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
    @FXML
    private Text startPageText;

    ProjectFactory factory = new ProjectFactory();

    public void createNewProject(ActionEvent event) throws IOException {
        factory.changeScene(loadProjectButton.getScene(),"projectoverview");
    }

    public void loadProject(ActionEvent event) {
        StreamJSON testStream = factory.makeStream();
        try {
            testStream.importFromJSON("project.json");
        } catch (IOException e) {
            startPageText.setText("404 Fel");
        }

    }
}
