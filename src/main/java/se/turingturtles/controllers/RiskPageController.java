package se.turingturtles.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.implementations.ProjectManagementImp;

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

    @FXML public void initialize(){

        riskName.setCellValueFactory(new PropertyValueFactory<>("name"));
        riskImpact.setCellValueFactory(new PropertyValueFactory<>("impact"));
        riskProbability.setCellValueFactory(new PropertyValueFactory<>("probability"));
        riskCalculated.setCellValueFactory(new PropertyValueFactory<>("riskCalculated"));
        riskIndex.setAutoRanging(false);
        riskIndex.setUpperBound(sortRisk());
        ObservableList<Risk> risk = FXCollections.observableArrayList(ProjectManagementImp.getProject().getRisk());
        riskMatrix.getData().add(loadRiskMatrix());
        riskDetails.setItems(risk);
        riskDetails.setVisible(false);
    }

    private int sortRisk(){
        int highestRisk = 0;
        ArrayList<Integer> risks = new ArrayList<>();
        for (int i = 0; i < ProjectManagementImp.getProject().getRisk().size(); i++){
            risks.add(ProjectManagementImp.getProject().getRisk().get(i).calculateRisk());
        }
        highestRisk = Collections.max(risks);
        return highestRisk;
    }
    private XYChart.Series<String, Integer> loadRiskMatrix(){
        List<Risk> risks = ProjectManagementImp.getProject().getRisk();
        XYChart.Series<String,Integer> series = new XYChart.Series<String, Integer>();
        for (Risk risk : risks) {
            series.getData().add(new XYChart.Data<String, Integer>(risk.getName(), risk.calculateRisk()));
        }
        series.setName("Risks");
        return series;
    }


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
