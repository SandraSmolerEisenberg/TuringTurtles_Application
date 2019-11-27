package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.Task;
import se.turingturtles.implementations.ProjectCalculationsImp;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ProjectOverviewController {
    @FXML
    private AnchorPane overviewTab;

    @FXML
    private Text projectName;

    @FXML
    private Text numberOfTasks;

    @FXML
    private Text numberOfMembers;

    @FXML
    private Text projectDuration;

    @FXML
    private StackedBarChart projectSchedule;

    @FXML
    private NumberAxis weeksAxis;

    @FXML
    private CategoryAxis taskAxis;
    private ProjectFactory projectFactory = new ProjectFactory();
    private ProjectManagement projectManagement = projectFactory.makeProjectManagement();

    // Default project information loaded into the page
    @FXML
    public void initialize(){
        projectName.setText(ProjectManagementImp.getProject().getName());
        numberOfTasks.setText("Total number of tasks: " + projectManagement.retrieveTasks().size());
        numberOfMembers.setText("Total number of members: " + projectManagement.getTeamMembers().size());
        projectDuration.setText("Project duration: " + ProjectManagementImp.getProject().getDuration() + "w");
        loadTaskData();


    }

    // loading the schedule into the Gannt Graph
    private void loadTaskData() {
        List<Task> tasks = projectManagement.retrieveTasks();
        XYChart.Series<Integer, String> series1 = new XYChart.Series<Integer, String>();
        XYChart.Series<Integer, String> series2 = new XYChart.Series<Integer, String>();
        weeksAxis.setAutoRanging(false);
        weeksAxis.setLowerBound(ProjectManagementImp.getProject().getStartWeek());
        weeksAxis.setLabel("Weeks");
        for (Task task : tasks) {
            int weekOfTask = task.getStartWeek() + task.getDuration();
            series1.getData().add(new XYChart.Data<Integer, String>(task.getStartWeek(), task.getName()));
            series2.getData().add(new XYChart.Data<Integer, String>(task.getDuration(), task.getName()));
        }
        projectSchedule.getData().addAll(series1,series2);
    }
}
