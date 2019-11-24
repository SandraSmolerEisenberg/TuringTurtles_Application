package se.turingturtles.controllers;

import java.awt.event.ActionListener;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import se.turingturtles.Main;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.streamIO.StreamJSON;

public class StartPageController {


    @FXML
    private Button createProjectButton;
    @FXML
    private Label disclaimerText;
    @FXML
    private Button loadProjectButton;
    @FXML
    private Text startPageText;

    public void createNewProject(ActionListener event) {
        event.actionPerformed(createProjectButton.hoverProperty().);
    }

    public void loadProject(ActionEvent event) {
        ProjectFactory factory = new ProjectFactory();
        StreamJSON testStream = factory.makeStream();
        try {
            testStream.importFromJSON("project.json");
        } catch (IOException e) {
            startPageText.setText("404 Fel");
        }

    }
}
