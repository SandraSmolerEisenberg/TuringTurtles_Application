package se.turingturtles.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import se.turingturtles.application.ProjectCalculations;
import se.turingturtles.application.ProjectManagement;
import se.turingturtles.application.Validator;
import se.turingturtles.entities.Task;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import se.turingturtles.streamIO.StreamIO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ProjectManagementController {

    @FXML private Text projectName3;
    @FXML private DatePicker projectStartWeek;
    @FXML private DatePicker projectEndWeek;
    @FXML private Button updateProjectDurationButton;
    @FXML private Button backToOverview;
    @FXML private AnchorPane changeDurationAnchorPane;
    //Mater anchor
    @FXML private AnchorPane projectManagementPage;
    //Project Management page
    @FXML private AnchorPane projectManagementAnchor;
    @FXML private Text projectName;
    @FXML private Text earnedValue;
    @FXML private Text costVariance;
    @FXML private Text scheduleVariance;
    @FXML private Text totalSalaries;
    @FXML private Text budget;
    @FXML private Button changeBudget;
    @FXML private Text startWeek;
    @FXML private Text endWeek;
    @FXML private Text duration;
    @FXML private Button changeDuration;
    @FXML private Text timeOnTasks;
    @FXML private Text completedTasks;
    @FXML private Text activeTasks;

    //Change Budget Page
    @FXML private AnchorPane changeBudgetPage;
    @FXML private Text projectName2;
    @FXML private Text currentBudget;
    @FXML private Text increaseBudget;
    @FXML private Text decreaseBudget;
    @FXML private TextField increaseBudgetAmount;
    @FXML private TextField decreaseBudgetAmount;
    @FXML private Button backButton;
    @FXML private Button setIncreaseAmount;
    @FXML private Button setDecreaseAmount;

    private ProjectFactory factory = new ProjectFactory();
    private StreamIO json = factory.makeStream();
    private ProjectManagement projectManagement = factory.makeProjectManagement();
    private ProjectCalculations projectCalculations = factory.makeProjectCalculations();
    private Validator validator = factory.makeValidator();

    @FXML
    public void initialize(){
        //Project management page
        updateValues();
        changeDurationAnchorPane.setVisible(false);
        changeBudget.setText("Change Budget");
        changeDuration.setText("Change Duration");

        //Change budget Page
        changeBudgetPage.setVisible(false);
        increaseBudget.setText("Increase budget: ");
        decreaseBudget.setText("Decrease budget");
        projectManagementPage.getStylesheets().add(getClass().getResource("/se/turingturtles/css/default.css").toExternalForm());


    }

    @FXML
    public void updateValues(){
        //Project management page
        projectName.setText(ProjectManagementImp.getProject().getName());
        earnedValue.setText("Earned value: " + ProjectManagementImp.getProject().getEarnedValue());
        costVariance.setText("Cost Variance: " + ProjectManagementImp.getProject().getCostVariance());
        scheduleVariance.setText("Schedule variance: " + ProjectManagementImp.getProject().getScheduleVariance());
        totalSalaries.setText("Total Salaries: " + projectCalculations.calculateTotalSalaries());
        budget.setText("Budget: " + ProjectManagementImp.getProject().getBudget());
        startWeek.setText("Start week: " + ProjectManagementImp.getProject().getStartWeek());
        endWeek.setText("End week: " + ProjectManagementImp.getProject().getEndWeek());
        duration.setText("Duration: " + ProjectManagementImp.getProject().getDuration());
        timeOnTasks.setText("Total time spent on project: " + projectManagement.timeSpentOnProject() + " hours");
        completedTasks.setText("No. completed tasks: " + countCompletedTasks());
        activeTasks.setText("No. Active or planned tasks: " + countActiveTasks());

        //Change budget Page
        projectName2.setText(ProjectManagementImp.getProject().getName());
        currentBudget.setText("Current budget: " + ProjectManagementImp.getProject().getBudget());
        projectName3.setText(ProjectManagementImp.getProject().getName());
    }
    @FXML
    public void invalidFormatAlert(){
        Alert invalidFormatAlert = new Alert(Alert.AlertType.CONFIRMATION);
        invalidFormatAlert.setGraphic(factory.loadErrorNode());
        invalidFormatAlert.setTitle("Invalid format");
        invalidFormatAlert.setHeaderText("Input should consist of only numbers (0-9)");
        invalidFormatAlert.showAndWait();
    }

    @FXML
    public void increaseBudget(ActionEvent event){
        String text = increaseBudgetAmount.getText();
        if(validator.validateNumericInput(text)) {
            try {
                double amount = Double.parseDouble(increaseBudgetAmount.getText());
                projectCalculations.increaseBudget(amount);
                json.exportToStreamIO();
                increaseBudgetAmount.clear();
                updateValues();
            }catch(Exception exception) {
                if (text.contains(",")) {
                    Alert invalidFormatAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    invalidFormatAlert.setGraphic(factory.loadErrorNode());
                    invalidFormatAlert.setTitle("Invalid format");
                    invalidFormatAlert.setHeaderText("Decimal numbers should should be separated by \".\"");
                    invalidFormatAlert.showAndWait();
                } else {
                    Alert invalidFormatAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    invalidFormatAlert.setGraphic(factory.loadErrorNode());
                    invalidFormatAlert.setTitle("Invalid format");
                    invalidFormatAlert.setHeaderText("Decimal numbers should should be separated by \",\"");
                    invalidFormatAlert.showAndWait();
                }
            }
        }else{
            invalidFormatAlert();
        }
    }
    @FXML
    public void decreaseAmount(ActionEvent event){
        String text = decreaseBudgetAmount.getText();
        if(validator.validateNumericInput(text)) {
            double amount = Double.parseDouble(decreaseBudgetAmount.getText());
            projectCalculations.decreaseBudget(amount);
            json.exportToStreamIO();
            decreaseBudgetAmount.clear();
            updateValues();
        }else{
            invalidFormatAlert();
        }
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
        loadChangeDurationPage();

    }

    private void loadChangeDurationPage() {
            if (projectManagementAnchor.isVisible()){
                projectManagementAnchor.setVisible(false);
                changeDurationAnchorPane.setVisible(true);
                setDatePicker(projectStartWeek, "StartDate");
                setDatePicker(projectEndWeek, "EndDate");
            }

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


    public void updateProjectDuration(ActionEvent event) {

        LocalDate projectStartDate = projectStartWeek.getValue();
        LocalDate projectEndDate = projectEndWeek.getValue();

        if(!validator.validateDate(projectStartDate, projectEndDate)){
            projectStartWeek.getEditor().clear();
            projectEndWeek.getEditor().clear();
            projectStartWeek.setPromptText("Choose date:");
            projectEndWeek.setPromptText("Choose date:");
            Alert selectionError = new Alert(Alert.AlertType.ERROR);
            selectionError.setGraphic(factory.loadErrorNode());
            selectionError.setTitle("Error!");
            selectionError.setHeaderText("No or invalid date selected!");
            selectionError.setContentText("Please select a valid date from the.");
            selectionError.showAndWait();
        }
        else {
            ProjectManagementImp.getProject().setProjectStartDate(projectStartDate);
            ProjectManagementImp.getProject().setProjectEndDate(projectEndDate);
            json.exportToStreamIO();
            projectStartWeek.getEditor().clear();
            projectEndWeek.getEditor().clear();
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Success!");
            confirmationAlert.setGraphic(factory.loadNode());
            confirmationAlert.setHeaderText("Successfully updated the duration.");
            confirmationAlert.setContentText("The project start and/or end date has been updated.");
            confirmationAlert.showAndWait();
            updateValues();
        }

    }

    private void setDatePicker(DatePicker datePicker, String calendar){
        datePicker.setEditable(false);
        StringConverter<LocalDate> defaultConverter = datePicker.getConverter();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (calendar.equals("StartDate")) {
                    setDisable(empty || date.getDayOfWeek() != DayOfWeek.MONDAY);
                }
                else if (calendar.equals("EndDate")){
                    setDisable(empty || date.getDayOfWeek() != DayOfWeek.SUNDAY);
                }
            }
        });
        datePicker.setPromptText("Choose date:");
    }

    public void backToOverviewPage(){
        changeDurationAnchorPane.setVisible(false);
        projectManagementAnchor.setVisible(true);
        updateValues();
    }
}
