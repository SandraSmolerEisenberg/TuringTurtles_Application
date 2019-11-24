package se.turingturtles.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import se.turingturtles.Main;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.streamIO.StreamJSON;

import java.io.IOException;

public class ProjectFactory {

    public void changeScene(Scene scene, String fxml) throws IOException {

        scene.setRoot(setFXML(fxml));
    }


    public Parent setFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmlfiles/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public ProjectManagementImp createProjectManagement(){
        return new ProjectManagementImp();
    }
    public ProjectCalculationsImp createProjectCalculation(){
        return new ProjectCalculationsImp();
    }


    public Image loadImage(String image){
        return new Image("se/turingturtles/images/" + image + ".png");
    }

    public Risk createRisk(String name, int impact, int probability){
        return new Risk(name, impact, probability);
    }

    public TeamMember createTeamMember(String name, int id, double hourlyWage){
        return new TeamMember(name, id, hourlyWage);
    }

    public ObjectMapper makeObjectMapper(){
        return new ObjectMapper();
    }

    public Task createTask(String name,int startWeek, int duration){
        return new Task(name,startWeek, duration);
    }

    public Project createProject(String name, double budget, int duration){
        return new Project(name, budget, duration);
    }

    public StreamJSON makeStream(){
        return new StreamJSON();
    }

    public Project createProject(){
        return ProjectManagementImp.getProject();
    }
}
