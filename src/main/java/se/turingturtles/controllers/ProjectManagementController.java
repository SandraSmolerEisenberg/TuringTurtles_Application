package se.turingturtles.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    //Mater anchor
    @FXML
    private AnchorPane projectManagementPage;

    //Project Management page
    @FXML
    private AnchorPane projectManagementAnchor;
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

    //Change Budget Page
    @FXML
    private AnchorPane changeBudgetPage;
    @FXML
    private Text projectName2;
    @FXML
    private Text currentBudget;
    @FXML
    private Text increaseBudget;
    @FXML
    private Text decreaseBudget;
    @FXML
    private TextField increaseBudgetAmount;
    @FXML
    private TextField decreaseBudgetAmount;
    @FXML
    private Button backButton;
    @FXML
    private Button setIncreaseAmount;
    @FXML
    private Button setDecreaseAmount;



    private ProjectFactory factory = new ProjectFactory();
    @FXML
    ProjectManagement projectManagement = factory.makeProjectManagement();
    ProjectCalculations projectCalculations = factory.makeProjectCalculations();
    Validator validator = factory.makeValidator();

    @FXML
    public void initialize(){
        //Project management page
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

        //Change budget Page
        changeBudgetPage.setVisible(false);
        projectName2.setText(ProjectManagementImp.getProject().getName());
        currentBudget.setText("Current budget: " + ProjectManagementImp.getProject().getBudget());
        increaseBudget.setText("Increase budget: ");
        decreaseBudget.setText("Decrease budget");

    }

    @FXML
    public void increaseBudget(ActionEvent event){
        double amount = Double.parseDouble(increaseBudgetAmount.getText());
        projectCalculations.increaseBudget(amount);
    }
    @FXML
    public void decreaseAmount(ActionEvent event){
        double amount = Double.parseDouble(decreaseBudgetAmount.getText());
        projectCalculations.decreaseBudget(amount);
    }

    @FXML
    public void goBack(){
        changeBudgetPage.setVisible(false);
        increaseBudgetAmount.clear();
        decreaseBudgetAmount.clear();
        projectManagementAnchor.setVisible(true);
    }

    @FXML
    public void changeBudget(ActionEvent event){
        projectManagementAnchor.setVisible(false);
        changeBudgetPage.setVisible(true);

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
    @FXML
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
