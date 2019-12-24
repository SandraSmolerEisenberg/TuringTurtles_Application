package se.turingturtles.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import se.turingturtles.application.ProjectManagement;
import se.turingturtles.entities.Task;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ProjectOverviewController {

    @FXML private Button scheduleButton;
    @FXML private AnchorPane overviewTab;
    @FXML private Text projectName;
    @FXML private Text numberOfTasks;
    @FXML private Text numberOfMembers;
    @FXML private Text projectDuration;
    @FXML private StackedBarChart projectSchedule;
    @FXML private NumberAxis weeksAxis;
    @FXML private CategoryAxis taskAxis;

    private ProjectFactory projectFactory = new ProjectFactory();
    private ProjectManagement projectManagement = projectFactory.makeProjectManagement();
    private static final int WEEKS_PER_YEAR = 52;




    // Default project information loaded into the page
    @FXML
    public void initialize(){
        projectName.setText(ProjectManagementImp.getProject().getName());
        numberOfTasks.setText("Total number of tasks: " + projectManagement.retrieveTasks().size());
        numberOfMembers.setText("Total number of members: " + projectManagement.getTeamMembers().size());
        projectDuration.setText("Project duration: " + ProjectManagementImp.getProject().getDuration() + "w");
        loadTaskData();
    }

    // loading the schedule into the Gannt Graph setting the lower and upper bound of the X axis

    private void loadTaskData() {
        List<Task> tasks = projectManagement.retrieveTasks();
        XYChart.Series<Integer, String> series1 = new XYChart.Series<Integer, String>();
        XYChart.Series<Integer, String> series2 = new XYChart.Series<Integer, String>();
        weeksAxis.setAutoRanging(false);
        weeksAxis.setLowerBound(ProjectManagementImp.getProject().getStartWeek());
        weeksAxis.setUpperBound(ProjectManagementImp.getProject().getStartWeek() + ProjectManagementImp.getProject().getDuration());
        weeksAxis.setLabel("Weeks");
        taskAxis.setLabel("Tasks");
        // The code works for projects not longer of 2 years
        weeksAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {

                int week = (int) object.doubleValue();
                if (week > WEEKS_PER_YEAR) {
                    week = week -WEEKS_PER_YEAR;
                }
                return "" + week;
            }
            @Override
            public Number fromString(String s) {
                return null;
            }
        });
        for (Task task : tasks) {
            //Works for projects less then 1 year
            if (task.getStartDate().isAfter(ProjectManagementImp.getProject().getProjectStartDate()) && task.getStartWeek() < ProjectManagementImp.getProject().getStartWeek()){
                series1.getData().add(new XYChart.Data<Integer, String>(task.getStartWeek() + WEEKS_PER_YEAR, task.getName()));
                series2.getData().add(new XYChart.Data<Integer, String>(task.getDuration(), task.getName()));
            }
            else {
                series1.getData().add(new XYChart.Data<Integer, String>(task.getStartWeek(), task.getName()));
                series2.getData().add(new XYChart.Data<Integer, String>(task.getDuration(), task.getName()));
            }

        }
        series1.setName("Tasks");
        weeksAxis.setTickUnit(1.0);
        projectSchedule.getData().setAll(series1,series2);
        projectSchedule.setLegendVisible(false);
    }

    public void updateFields(){
        numberOfTasks.setText("Total number of tasks: " + projectManagement.retrieveTasks().size());
        numberOfMembers.setText("Total number of members: " + projectManagement.getTeamMembers().size());
        projectDuration.setText("Project duration: " + ProjectManagementImp.getProject().getDuration() + "w");

    }


    public void updateSchedule(javafx.event.ActionEvent event) throws IOException {
        projectFactory.changeScene(overviewTab.getScene(),"projectmaster");
    }
}
