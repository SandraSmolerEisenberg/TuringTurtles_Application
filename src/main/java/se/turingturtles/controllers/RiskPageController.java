package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import se.turingturtles.entities.Risk;
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
    private TableColumn<Risk, Integer> riskCalculated;
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

    public void initialize(){
        riskName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        riskImpact.setCellValueFactory(new PropertyValueFactory<>("Impact"));
        riskProbability.setCellValueFactory(new PropertyValueFactory<>("Probability"));
        riskIndex.setAutoRanging(false);
        riskIndex.setUpperBound(sortRisk());
        List<Risk> riskList = ProjectManagementImp.getProject().getRisk();
        ObservableList<Risk> risks = FXCollections.observableArrayList();
        risks.addAll(riskList);
        riskMatrix.getData().add(loadRiskMatrix());
        riskDetails.setItems(risks);
        riskDetails.setVisible(false);
    }

    public int sortRisk(){
        int highestRisk = 0;
        ArrayList<Integer> risks = new ArrayList<>();
        for (int i = 0; i < ProjectManagementImp.getProject().getRisk().size(); i++){
            risks.add(ProjectManagementImp.getProject().getRisk().get(i).calculateRisk());
        }
        highestRisk = Collections.max(risks);
        return highestRisk;
    }
    public XYChart.Series<String, Integer> loadRiskMatrix(){
        List<Risk> risks = ProjectManagementImp.getProject().getRisk();
        XYChart.Series<String,Integer> series = new XYChart.Series<String, Integer>();
        for (int i = 0; i < risks.size(); i++) {
            series.getData().add(new XYChart.Data<String, Integer>(risks.get(i).getName(), risks.get(i).calculateRisk()));
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
