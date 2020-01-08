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
import se.turingturtles.application.ProjectManagement;
import se.turingturtles.application.Validator;
import se.turingturtles.entities.Risk;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import se.turingturtles.streamIO.StreamIO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiskPageController {

    @FXML private BorderPane riskPage;
    @FXML private TextField newRiskName;
    @FXML private TextField newRiskProbability;
    @FXML private TextField newRiskImpact;
    @FXML private Button createRiskButton;
    @FXML private Button addRiskButton;
    @FXML private AnchorPane createRiskAnchorPane;
    @FXML private AnchorPane riskDetails;
    @FXML private Button deleteRiskButton;
    @FXML private TableColumn<Risk, String> riskName;
    @FXML private TableColumn<Risk, Double> riskImpact;
    @FXML private TableColumn<Risk, Double> riskProbability;
    @FXML private BarChart<String, Double> riskMatrix;
    @FXML private CategoryAxis riskType;
    @FXML private TableView<Risk> riskDetailsTable;
    @FXML private NumberAxis riskIndex;
    @FXML private Button riskDetailsButton;
    @FXML private TableColumn<Risk,String> riskCalculated;

    private ProjectFactory factory = new ProjectFactory();
    private StreamIO streamIO = factory.makeStream();
    private ProjectManagement projectManagement = factory.makeProjectManagement();


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
        riskPage.getStylesheets().add(getClass().getResource("/se/turingturtles/css/default.css").toExternalForm());
    }

    // Return the highest risk value and use it for the matrix
    private Double getHighestRisk(){
        Double highestRisk;
        List<Risk> risks = projectManagement.getProjectRisks();
        ArrayList<Double> risksValues = new ArrayList<>();
        for (Risk risk : risks) {
            risksValues.add(risk.calculateRisk());
        }
        highestRisk = Collections.max(risksValues);
        return highestRisk;
    }

    //Read, load and return the data for the matrix
    private XYChart.Series<String, Double> loadRiskMatrix(){
        List<Risk> risks = projectManagement.getProjectRisks();
        XYChart.Series<String,Double> series = new XYChart.Series<String, Double>();
        for (Risk risk : risks) {
            series.getData().add(new XYChart.Data<String, Double>(risk.getName(), risk.calculateRisk()));
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
        Validator validator = factory.makeValidator();
        if (validator.validateTextInput(name) && validator.validateNumericInput(probability) && validator.validateNumericInput(impact)){
            double impactValue;
            double probabilityValue;
            try{
                impactValue = Double.parseDouble(impact);
                probabilityValue = Double.parseDouble(probability);
            }
            catch (Exception e){
                newRiskImpact.clear();
                newRiskImpact.setPromptText("Invalid impact");
                newRiskProbability.clear();
                newRiskProbability.setPromptText("Invalid Probability");
                impactValue = 11;
                probabilityValue = 11;
                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                deleteAlert.setTitle("Invalid selection");
                deleteAlert.setGraphic(factory.loadErrorNode());
                deleteAlert.setHeaderText("Hey!");
                deleteAlert.setContentText("Probability and impact are non-decimal numbers\n between 0 and 1!");
                deleteAlert.showAndWait();
            }

            if (impactValue > 1 || probabilityValue > 1) {
                if (impactValue > 1) {
                    newRiskImpact.clear();
                    newRiskImpact.setPromptText("Invalid impact");

                }
                if (probabilityValue > 1) {
                    newRiskProbability.clear();
                    newRiskProbability.setPromptText("Invalid Probability");
                }
            }
            else {
                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Success!");
                confirmationAlert.setGraphic(factory.loadNode());
                confirmationAlert.setHeaderText("Successfully added risk: " + newRiskName.getText() + ".");
                confirmationAlert.setContentText("The following information was added: " + "\n" + "Description: " + newRiskName.getText() + "\n" + "Impact: " + newRiskImpact.getText() + " Probability: " + newRiskProbability.getText());
                confirmationAlert.showAndWait();
                projectManagement.createRisk(name, impactValue, probabilityValue);
                streamIO.exportToStreamIO();
                clearInputFields();
                loadRiskDetailsTable();
                riskIndex.setUpperBound(getHighestRisk());
                riskMatrix.getData().setAll(loadRiskMatrix());
            }
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
        riskDetailsTable.setItems(risk);
    }

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

    public void deleteRisk(ActionEvent event) {
        Risk risk = riskDetailsTable.getSelectionModel().getSelectedItem();
        if (risk == null) {
            Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
            deleteAlert.setGraphic(factory.loadErrorNode());
            deleteAlert.setTitle("Selection missing");
            deleteAlert.setHeaderText("Hey!");
            deleteAlert.setContentText("Please select a risk to delete!");
            deleteAlert.showAndWait();
        } else {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("You are about to delete this risk from the risk list");
            deleteAlert.setGraphic(factory.loadErrorNode());
            deleteAlert.setHeaderText("WARNING!");
            deleteAlert.setContentText("You have selected to delete the following risk: \nDescription: " + risk.getName() + "\n\nPlease click OK, in order to proceed!");
            deleteAlert.showAndWait();
            if (deleteAlert.getResult() == ButtonType.OK) {

                ProjectManagementImp.getProject().getRisk().remove(risk);
                streamIO.exportToStreamIO();
                loadRiskDetailsTable();
                riskDetailsTable.refresh();
                riskMatrix.getData().setAll(loadRiskMatrix());

            }
        }
    }
}
