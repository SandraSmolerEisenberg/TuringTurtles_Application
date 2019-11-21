package se.turingturtles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.turingturtles.implementations.MainFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage window) throws IOException {
        MainFactory mainFactory= new MainFactory();
        Scene scene = new Scene(mainFactory.setFXML("startpage"));
        window.setScene(scene);
        window.setTitle("Turing Turtles");
        Image image = mainFactory.loadImage("turtle");
        window.getIcons().add(image);
        window.show();
    }


//    //Default method to change scenes(pages)
//    public static void changeScene(Scene scene, String fxml) throws IOException {
//
//        scene.setRoot(setFXML(fxml));
//    }

    public static void main(String[] args)
    {

        launch();
    }




}