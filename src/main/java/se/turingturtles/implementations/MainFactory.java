package se.turingturtles.implementations;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import se.turingturtles.Main;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.TeamMember;

import java.io.IOException;

public class MainFactory {

    public Parent setFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmlfiles/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public Image loadImage(String image){
        return new Image("se/turingturtles/images/" + image + ".png");
    }

    public Risk createRisk(){
        return new Risk();
    }

    public TeamMember createTeamMember(){
        return new TeamMember();
    }

    public Task createTask(){
        return new Task();
    }

    public Project createProject(String name, double budget, int duration){
        return new Project(name, budget, duration);
    }

    public Project createProject(){
        return new Project();
    }
}