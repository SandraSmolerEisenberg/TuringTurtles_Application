package se.turingturtles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;

    // Default start method for javafx. Starts the main window(stage) and crates a scene(page) by loading a fxml file with
    //the help of the method setFXML by sending the name of the file
    @Override
    public void start(Stage window) throws IOException {
        scene = new Scene(setFXML("startpage"));
        window.setScene(scene);
        window.setTitle("The Turing Turtles");
        window.show();
    }

    //Default method to change scenes(pages)
    public static void changeScene(String fxml) throws IOException {
        scene.setRoot(setFXML(fxml));
    }

    //loads the Fxml file from the directory and returns it so it can be set in the window
    private static Parent setFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmlfiles/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}