package se.turingturtles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import se.turingturtles.ProjectCalculations;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.Task;
import se.turingturtles.implementations.ProjectCalculationsImp;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import java.util.List;
import java.awt.*;

import static java.lang.Integer.parseInt;

public class ProjectManagementController {
    @FXML
    private Text projectName;
    @FXML
    private Text earnedValue;
    @FXML
    private Text costVariance;
    @FXML
    private Text scheduleVariance;
    @FXML
    private Text totalSalaries;
    @FXML
    private Text budget;
    @FXML
    private Button changeBudget;
    @FXML
    private Text startWeek;
    @FXML
    private Text endWeek;
    @FXML
    private Text duration;
    @FXML
    private Button changeDuration;
    @FXML
    private Text timeOnTasks;
    @FXML
    private Text completedTasks;
    @FXML
    private Text activeTasks;

    private ProjectFactory factory = new ProjectFactory();
    ProjectManagement projectManagement = factory.makeProjectManagement();
    ProjectCalculations projectCalculations = factory.makeProjectCalculations();
    Validator validator = factory.makeValidator();

    @FXML
    public void initialize(){
        projectName.setText(ProjectManagementImp.getProject().getName());
        earnedValue.setText("Earned value: " + ProjectManagementImp.getProject().getEarnedValue());
        costVariance.setText("Cost Variance: " + ProjectManagementImp.getProject().getCostVariance());
        scheduleVariance.setText("Schedule variance: " + ProjectManagementImp.getProject().getScheduleVariance());
        totalSalaries.setText("Total Salaries: " + projectCalculations.calculateTotalSalaries());
        budget.setText("Budget: " + ProjectManagementImp.getProject().getBudget());
        changeBudget.setText("Change Budget");
        startWeek.setText("Start week: " + ProjectManagementImp.getProject().getStartWeek());
        endWeek.setText("End week: " + ProjectManagementImp.getProject().getEndWeek());
        duration.setText("Duration: " + ProjectManagementImp.getProject().getDuration());
        changeDuration.setText("Change Duration");
        timeOnTasks.setText("Total time spent on project: " + projectManagement.timeSpentOnProject() + " Weeks");
        completedTasks.setText("No. completed tasks: " + countCompletedTasks());
        activeTasks.setText("No. Active or planned tasks: " + countActiveTasks());




    }
    @FXML
    public void changeBudget(){
        int newBudget = Integer.parseInt(changeBudget.getText());
        ProjectManagementImp.getProject().setBudget(newBudget);
    }
    @FXML
    public void changeDuration(){
        int newDuration = Integer.parseInt(changeBudget.getText());
        ProjectManagementImp.getProject().setDuration(newDuration);
    }
    @FXML
    public int countCompletedTasks(){
        int fullCounter = 0;
        List<Task> tasks = projectManagement.retrieveTasks();
        for(Task task: tasks){
            if(task.getCompletion().equalsIgnoreCase("Completed")){
                fullCounter++;
            }
        }
        return fullCounter;
    }
    public int countActiveTasks(){
        int fullCounter = 0;
        List<Task> tasks = projectManagement.retrieveTasks();
        for(Task task: tasks){
            if(task.getCompletion().equalsIgnoreCase("Not Completed")){
                fullCounter++;
            }
        }
        return fullCounter;
    }


}
