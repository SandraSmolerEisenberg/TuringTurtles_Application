package se.turingturtles.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import se.turingturtles.Main;
import se.turingturtles.ProjectCalculations;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.streamIO.StreamJSON;

import java.io.IOException;
import java.time.LocalDate;

public class ProjectFactory {

    public void changeScene(Scene scene, String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmlfiles/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public ProjectManagement makeProjectManagement(){
        return new ProjectManagementImp();
    }

    public Validator makeValidator(){
        return new ValidatorImp();
    }

    public ProjectCalculations makeProjectCalculations(){
        return new ProjectCalculationsImp();
    }


    public Image loadImage(String image){
        return new Image("se/turingturtles/images/" + image + ".png");
    }

    public Risk makeRisk(String name, int impact, int probability){
        return new Risk(name, impact, probability);
    }

    public TeamMember makeTeamMember(String name, int id, double hourlyWage){
        return new TeamMember(name, id, hourlyWage);
    }

    public ObjectMapper makeObjectMapper(){
        return new ObjectMapper();
    }

    public Task makeTask(String name, LocalDate startDate, LocalDate endDate){
        return new Task(name, startDate, endDate);
    }

    public Project makeProject(String name, double budget, LocalDate projectStart, LocalDate projectEnd){
        return new Project(name, budget, projectStart, projectEnd);
    }

    public StreamJSON makeStream(){
        return new StreamJSON();
    }

    public Project makeProject(){
        return ProjectManagementImp.getProject();
    }
}
