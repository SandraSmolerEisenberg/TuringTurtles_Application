package se.turingturtles.implementations;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import se.turingturtles.Main;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;

import java.io.IOException;

public class MainFactory {


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

    public Task createTask(String name,int startWeek, int duration){
        return new Task(name,startWeek, duration);
    }

    public Project createProject(String name, double budget, int duration){
        return new Project(name, budget, duration);
    }

    public Project createProject(){
        return ProjectManagementImp.getProject();
    }
}
