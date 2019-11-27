package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.Risk;
import se.turingturtles.implementations.ProjectFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiskPageController {

    @FXML
    private TableColumn<Risk, String> riskName;
    @FXML
    private TableColumn<Risk, Integer> riskImpact;
    @FXML
    private TableColumn<Risk, Integer> riskProbability;
    @FXML
    private BarChart<String, Integer> riskMatrix;
    @FXML
    private CategoryAxis riskType;
    @FXML
    private TableView<Risk> riskDetails;
    @FXML
    private NumberAxis riskIndex;
    @FXML
    private Button riskDetailsButton;
    @FXML
    private TableColumn<Risk,String> riskCalculated;
    private ProjectFactory projectFactory = new ProjectFactory();
    private ProjectManagement projectManagement = projectFactory.makeProjectManagement();


    //Load data into matrix and initialize table and set it to not visible by default
    @FXML public void initialize(){

        riskName.setCellValueFactory(new PropertyValueFactory<>("name"));
        riskImpact.setCellValueFactory(new PropertyValueFactory<>("impact"));
        riskProbability.setCellValueFactory(new PropertyValueFactory<>("probability"));
        riskCalculated.setCellValueFactory(new PropertyValueFactory<>("riskCalculated"));
        riskIndex.setAutoRanging(false);
        riskIndex.setUpperBound(getHighestRisk());
        ObservableList<Risk> risk = FXCollections.observableArrayList(projectManagement.getProjectRisks());
        riskMatrix.getData().add(loadRiskMatrix());
        riskDetails.setItems(risk);
        riskDetails.setVisible(false);
    }

    // Return the highest risk value and use it for the matrix
    private int getHighestRisk(){
        int highestRisk = 0;
        List<Risk> risks = projectManagement.getProjectRisks();
        ArrayList<Integer> risksValues = new ArrayList<>();
        for (int i = 0; i < risks.size(); i++){
            risksValues.add(risks.get(i).calculateRisk());
        }
        highestRisk = Collections.max(risksValues);
        return highestRisk;
    }
    //Read, load and return the data for the matrix
    private XYChart.Series<String, Integer> loadRiskMatrix(){
        List<Risk> risks = projectManagement.getProjectRisks();
        XYChart.Series<String,Integer> series = new XYChart.Series<String, Integer>();
        for (Risk risk : risks) {
            series.getData().add(new XYChart.Data<String, Integer>(risk.getName(), risk.calculateRisk()));
        }
        series.setName("Risks");
        return series;
    }


    // Switch between matrix and table
    public void showDetails(javafx.event.ActionEvent event) {
        if (riskMatrix.isVisible()){
            riskMatrix.setVisible(false);
            riskDetails.setVisible(true);
            riskDetailsButton.setText("Show Matrix");
        }
        else {
            riskDetails.setVisible(false);
            riskMatrix.setVisible(true);
            riskDetailsButton.setText("View Details");
        }

    }


}
