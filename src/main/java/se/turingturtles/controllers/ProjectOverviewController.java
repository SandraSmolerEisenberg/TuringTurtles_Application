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
import se.turingturtles.entities.Task;
import se.turingturtles.implementations.ProjectCalculationsImp;
import se.turingturtles.implementations.ProjectManagementImp;

import java.util.ArrayList;
import java.util.Arrays;
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

    @FXML
    public void initialize(){
        projectName.setText(ProjectManagementImp.getProject().getName());
        numberOfTasks.setText("Total number of tasks: " + ProjectManagementImp.getProject().getTasks().size());
        numberOfMembers.setText("Total number of members: " + ProjectManagementImp.getProject().getTeamMembers().size());
        projectDuration.setText("Project duration: " + ProjectManagementImp.getProject().getDuration() + "w");

        projectSchedule.getData().add(loadTaskData());


    }

    private XYChart.Series<Integer, String> loadTaskData() {
        List<Task> tasks = ProjectManagementImp.getProject().getTasks();
        XYChart.Series<Integer, String> series = new XYChart.Series<Integer, String>();
        weeksAxis.setAutoRanging(false);
        weeksAxis.setLowerBound(ProjectManagementImp.getProject().getStartWeek());

        for (int i = 0; i < tasks.size(); i++) {
           // weeksAxis.setLowerBound(tasks.get(i).getStartWeek());

            series.getData().add(tasks.get(i).getStartWeek(), new XYChart.Data<Integer, String>(tasks.get(i).getStartWeek() + tasks.get(i).getDuration(), tasks.get(i).getName()));

        }
        series.setName("Tasks");
        System.out.println(weeksAxis.getLowerBound());
        System.out.println(weeksAxis.getLowerBound());
        return series;
    }
}
