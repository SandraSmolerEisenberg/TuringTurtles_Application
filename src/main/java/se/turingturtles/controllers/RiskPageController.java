package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.Risk;
import se.turingturtles.implementations.ProjectFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiskPageController {

    public BorderPane riskpage;
    public TextField newRiskName;
    public TextField newRiskProbability;
    public TextField newRiskImpact;
    public Button createRiskButton;
    public Button addRiskButton;
    public AnchorPane createRiskAnchorPane;
    @FXML private AnchorPane riskDetails;
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
    private TableView<Risk> riskDetailsTable;
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
        try{
            riskIndex.setUpperBound(getHighestRisk());
            riskMatrix.getData().add(loadRiskMatrix());
        }
        catch (Exception e){
            riskIndex.setUpperBound(0);
        }
        createRiskAnchorPane.setVisible(false);
        riskDetails.setVisible(false);
        loadRiskDetailsTable();
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
    public void showDetails(ActionEvent event) {
        clearInputFields();
        if (riskMatrix.isVisible()){
            riskMatrix.setVisible(false);
            riskDetails.setVisible(true);
            riskDetailsButton.setText("Show Matrix");
        }
        else if (createRiskAnchorPane.isVisible())
        {
            riskDetails.setVisible(true);
            riskMatrix.setVisible(false);
            createRiskAnchorPane.setVisible(false);
            riskDetailsButton.setText("Show Matrix");
            addRiskButton.setText("Add Risk");
        }
        else {
            riskDetails.setVisible(false);
            riskMatrix.setVisible(true);
            riskDetailsButton.setText("Risk Details");
        }

    }


    public void createRisk(ActionEvent event) {
        String name = newRiskName.getText();
        String probability = newRiskProbability.getText();
        String impact = newRiskImpact.getText();
        Validator validator = projectFactory.makeValidator();
        if (validator.validateTextInput(name) && validator.validateNumericInput(probability) && validator.validateNumericInput(impact)){
            int impactValue = Integer.parseInt(impact);
            int probabilityValue = Integer.parseInt(probability);
            if (impactValue > 10 ){
                newRiskImpact.clear();
                newRiskImpact.setPromptText("Invalid impact");

            }
            if ( probabilityValue > 10){
                newRiskProbability.clear();
                newRiskProbability.setPromptText("Invalid Probability");
            }
            projectManagement.createRisk(name, impactValue , probabilityValue);
            clearInputFields();
            loadRiskDetailsTable();
            riskIndex.setUpperBound(getHighestRisk());
            riskMatrix.getData().setAll(loadRiskMatrix());
        }
        else if (!validator.validateTextInput(name) || !validator.validateNumericInput(impact) || !validator.validateNumericInput(probability)) {

           if (!validator.validateTextInput(name)) {
               newRiskName.clear();
               newRiskName.setPromptText("Invalid Description");
           }
            if (!validator.validateNumericInput(impact)) {
                newRiskImpact.clear();
                newRiskImpact.setPromptText("Invalid Impact");
            }
            if (!validator.validateNumericInput(probability)) {
                newRiskProbability.clear();
                newRiskProbability.setPromptText("Invalid Probability");
            }


            }
    }

    private void clearInputFields() {
        newRiskName.clear();
        newRiskProbability.clear();
        newRiskImpact.clear();
        newRiskProbability.setPromptText("risk probability");
        newRiskImpact.setPromptText("risk impact");
        newRiskName.setPromptText("risk description");

    }

    private void loadRiskDetailsTable(){
        ObservableList<Risk> risk = FXCollections.observableArrayList(projectManagement.getProjectRisks());
        riskDetailsTable.setItems(risk);}

    public void loadCreateRiskAnchorPane(ActionEvent event) {
        clearInputFields();
        if (!createRiskAnchorPane.isVisible()) {
            riskDetails.setVisible(false);
            riskMatrix.setVisible(false);
            createRiskAnchorPane.setVisible(true);
            addRiskButton.setText("View Matrix");
            riskDetailsButton.setText("View Details");
        }
        else {
            riskDetails.setVisible(false);
            riskMatrix.setVisible(true);
            createRiskAnchorPane.setVisible(false);
            addRiskButton.setText("Add Risk");
        }
    }

}
