package se.turingturtles.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.turingturtles.App;
import se.turingturtles.application.ProjectCalculations;
import se.turingturtles.application.ProjectManagement;
import se.turingturtles.application.Validator;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.streamIO.StreamIO;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class ProjectFactory {

    public void changeScene(Scene scene, String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxmlfiles/" + fxml + ".fxml"));
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

    public ImageView loadErrorNode(){
        Image image = new Image("se/turingturtles/images/redturtlenode.png");
        ImageView imageView = new ImageView(image);
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() * 0.6;
        imageView.setFitWidth(width / 14);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public ImageView loadNode(){
            Image image = new Image("se/turingturtles/images/turtlenode.png");
            ImageView imageView = new ImageView(image);
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth() * 0.6;
            imageView.setFitWidth(width / 14);
            imageView.setPreserveRatio(true);
            return imageView;
    }
    public Image loadImage(String image){
        return new Image("se/turingturtles/images/" + image + ".png");
    }

    public Risk makeRisk(String name, double impact, double probability){
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

    public StreamIO makeStream(){
        return new StreamIO();
    }

    public Project makeProject(){
        return ProjectManagementImp.getProject();
    }
}
