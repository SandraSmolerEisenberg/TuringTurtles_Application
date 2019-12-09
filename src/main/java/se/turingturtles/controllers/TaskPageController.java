package se.turingturtles.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import se.turingturtles.ProjectManagement;
import se.turingturtles.Validator;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskPageController {
    @FXML
    public AnchorPane taskPage;

    @FXML
    public TableColumn taskEndWeek;

    @FXML
    public Button viewTaskButton;

    @FXML
    public Label viewTaskErrorMsg;
    public TableView teamMembersTable;
    public TableColumn teamMemberNameColumn;
    public TableColumn teamMemberIdColumn;
    public TableColumn teamMemberTotalTasks;
    public TableColumn teamMemberSalary;
    public Button assignTeamMemberButton;

    @FXML
    private TableView taskTableView;

    @FXML
    private TableColumn taskName;

    @FXML
    private TableColumn taskStartWeek;

    @FXML
    private TableColumn taskDuration;

    @FXML
    private TableColumn taskTeamMembersAmount;

    @FXML
    private TableColumn taskStatus;

    @FXML
    private Button taskCreateTaskButton;

    @FXML
    private Text taskHeaderText;

    @FXML
    private DatePicker taskStartDate;

    @FXML
    private DatePicker taskEndDate;

    @FXML
    private AnchorPane createTaskAnchorPane;

    @FXML
    private TextField newTaskName;

    @FXML
    private Button newTaskCreateButton;

    @FXML
    private AnchorPane tableAnchorPane;

    //-----Detailed View Attributes Start-----
    @FXML
    private AnchorPane taskDetailsAnchorPane;
    @FXML
    private Text taskDetailsViewHeaderText;
    @FXML
    private Text taskDetailsStartWeekText;
    @FXML
    private Text taskDetailsEndWeekText;
    @FXML
    private Text taskDetailsDurationText;
    @FXML
    private Text taskDetailsTeamMembersText;
    @FXML
    private Text taskDetailsStatusText;
    @FXML
    private ListView taskDetailsTeamMemberList;
    @FXML
    private Button taskDetailsEditTaskButton;
    @FXML
    private Button taskDetailsDeleteTaskButton;
    @FXML
    private Button taskDetailsAssignButton;


    //-----Detailed View Attributes End-----

    private ProjectFactory projectFactory = new ProjectFactory();
    private ProjectManagement projectManagement = projectFactory.makeProjectManagement();

    @FXML private void initialize(){
        tableAnchorPane.setVisible(true);
        loadTasksTable();
        setDatePicker(taskStartDate, "StartDate");
        setDatePicker(taskEndDate, "EndDate");
        createTaskAnchorPane.setVisible(false);
        taskDetailsAnchorPane.setVisible(false);
    }

    private void loadTasksTable(){
        taskName.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskStartWeek.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
        taskDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        taskTeamMembersAmount.setCellValueFactory(new PropertyValueFactory<>("totalTeamMembers"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("completion"));
        taskEndWeek.setCellValueFactory(new PropertyValueFactory<>("endWeek"));
        taskTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveTasks());
        taskTableView.setItems(tasks);
        taskTableView.refresh();

    }
    private void setDatePicker(DatePicker datePicker, String calendar){
        datePicker.setEditable(false);
        datePicker.setStyle(String.valueOf(ProjectManagementImp.getProject().getProjectStartDate()));
        StringConverter<LocalDate> defaultConverter = datePicker.getConverter();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate projectStartDay = ProjectManagementImp.getProject().getProjectStartDate();
                LocalDate projectFinalDay = ProjectManagementImp.getProject().getProjectEndDate();
                if (calendar.equals("StartDate")) {
                    setDisable(date.isAfter(projectFinalDay) || date.isBefore(projectStartDay) || date.getDayOfWeek() != DayOfWeek.MONDAY);
                }
                else if (calendar.equals("EndDate")){
                    setDisable(date.isAfter(projectFinalDay) || date.isBefore(projectStartDay) || date.getDayOfWeek() != DayOfWeek.SUNDAY);
                }
            }
        });
        datePicker.setPromptText("Choose date:");
    }

    @FXML public void createNewTask(ActionEvent event){
        if (tableAnchorPane.isVisible()){
            tableAnchorPane.setVisible(false);
            createTaskAnchorPane.setVisible(true);
            taskDetailsAnchorPane.setVisible(false);
            taskCreateTaskButton.setText("Back");
            viewTaskButton.setVisible(false);
            viewTaskErrorMsg.setVisible(false);
            loadTasksTable();
        }
        else {
            createTaskAnchorPane.setVisible(false);
            tableAnchorPane.setVisible(true);
            taskDetailsAnchorPane.setVisible(false);
            taskCreateTaskButton.setText("Create Task");

            viewTaskButton.setVisible(true);
            viewTaskErrorMsg.setText("");
            viewTaskErrorMsg.setVisible(true);
            loadTasksTable();
        }
    }

    @FXML public void makeNewTask(ActionEvent event){
        String name = newTaskName.getText();
        LocalDate taskStart = taskStartDate.getValue();
        LocalDate taskEnd = taskEndDate.getValue();
        taskEnd = taskEnd.plusDays(1);
        Validator validator = projectFactory.makeValidator();
        if (validator.validateTextInput(name) && validator.validateDate(taskStart, taskEnd)) {
            boolean sameName = false;
            ArrayList<Task> tasks = (ArrayList<Task>) projectManagement.retrieveTasks();
            for (int i = 0; i < tasks.size(); i++ ){
                if (tasks.get(i).getName().equals(name)) {
                    sameName = true;
                    break;
                }
            }
            if (sameName){
                newTaskName.clear();
                newTaskName.setPromptText("Invalid Name!");
            }
            else {
                projectManagement.createTask(name, taskStart, taskEnd);
                loadTasksTable();
                resetCreateTaskFields();
                newTaskName.clear();
            }
        }
        else if (!validator.validateTextInput(name)) {
            newTaskName.clear();
            newTaskName.setPromptText("Invalid Name!");
        }
        else if(!validator.validateDate(taskStart, taskEnd)){
            resetCreateTaskFields();
            taskStartDate.setPromptText("Set Valid Date!");
            taskEndDate.setPromptText("Set Valid Date!");
        }
    }

    private void resetCreateTaskFields(){
        taskStartDate.getEditor().clear();
        taskEndDate.getEditor().clear();
        newTaskName.setPromptText("Enter name:");
    }


    public void selectTaskRow(ActionEvent event){
        Task task = (Task) taskTableView.getSelectionModel().getSelectedItem();
        if(task == null ){
            viewTaskErrorMsg.setText("Select Task First");
            viewTaskErrorMsg.setStyle("-fx-text-fill: red;");
        }
        else{
            viewTaskErrorMsg.setVisible(false);
            loadViewTaskAnchorPane();
        }
    }

    private void loadViewTaskAnchorPane(){
        if (taskTableView.isVisible()){
            tableAnchorPane.setVisible(false);
            createTaskAnchorPane.setVisible(false);
            taskDetailsAnchorPane.setVisible(true);
            viewTaskButton.setVisible(false);
            taskCreateTaskButton.setText("Back");
            loadTasksTable();
        }
        else {
            taskDetailsAnchorPane.setVisible(false);
            createTaskAnchorPane.setVisible(false);
            tableAnchorPane.setVisible(true);
            viewTaskButton.setVisible(true);
            taskCreateTaskButton.setText("Create Task");
            loadTasksTable();
        }

        Task task = (Task) taskTableView.getSelectionModel().getSelectedItem();
        taskDetailsViewHeaderText.setText(task.getName());
        taskDetailsStartWeekText.setText("" + task.getStartWeek());
        taskDetailsEndWeekText.setText("" + task.getEndWeek());
        taskDetailsDurationText.setText("" + task.getDuration());
        taskDetailsTeamMembersText.setText("" + task.getTotalTeamMembers());
        taskDetailsStatusText.setText(task.getCompletion());
        loadTaskTeamMembersList(task);
        loadTeamMembersTable();
    }

    public void assignTeamMember(ActionEvent event){
        TeamMember teamMember = (TeamMember) teamMembersTable.getSelectionModel().getSelectedItem();
        Task task = projectManagement.findTask(taskDetailsViewHeaderText.getText());
        if (task != null && !task.getTeamMembers().contains(teamMember) && teamMember != null){
            projectManagement.assignTask(teamMember,task);
            loadTaskTeamMembersList(task);
            loadTeamMembersTable();
            loadTasksTable();
            taskDetailsTeamMembersText.setText("" + task.getTotalTeamMembers());
        }
        else if (task != null || teamMember != null){
            Alert assignmentError = new Alert(Alert.AlertType.ERROR);
            assignmentError.setTitle("Error!");
            assignmentError.setHeaderText("Assignment to task failed!");
            assignmentError.setContentText("Couldn't find team member" + ".");
            assignmentError.showAndWait();
        }
        else {
            Alert assignmentError = new Alert(Alert.AlertType.ERROR);
            assignmentError.setTitle("Error!");
            assignmentError.setHeaderText("Assignment to task failed!");
            assignmentError.setContentText(teamMember.getName() + " is already assigned to " + task.getName() + ".");
            assignmentError.showAndWait();
        }
    }

    private void loadTeamMembersTable() {
        teamMemberNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamMemberIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        teamMemberTotalTasks.setCellValueFactory(new PropertyValueFactory<>("totalTasks"));
        teamMemberSalary.setCellValueFactory(new PropertyValueFactory<>("hourlyWage"));
        ObservableList<TeamMember> teamMembers = FXCollections.observableArrayList(projectManagement.getTeamMembers());
        teamMembersTable.setItems(teamMembers);
        teamMembersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        teamMembersTable.refresh();
    }

    private void loadTaskTeamMembersList(Task task){
        ObservableList<TeamMember> teamMembers = FXCollections.observableArrayList(task.getTeamMembers());
        taskDetailsTeamMemberList.setItems(teamMembers);
    }
}
