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
import se.turingturtles.application.ProjectManagement;
import se.turingturtles.application.Validator;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.implementations.ProjectFactory;
import se.turingturtles.implementations.ProjectManagementImp;
import se.turingturtles.streamIO.StreamIO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskPageController {

    //Used for calculations, so we can have a count in week
    private static final int ONE_WEEK_DAY = 1;
    @FXML private RadioButton updateNameButton;
    @FXML private AnchorPane taskPage;
    @FXML private TableColumn taskEndWeek;
    @FXML private Button viewTaskButton;
    @FXML private RadioButton updateDateButton;
    @FXML private TableView taskTableView;
    @FXML private TableColumn taskName;
    @FXML private TableColumn taskStartWeek;
    @FXML private TableColumn taskDuration;
    @FXML private TableColumn taskTeamMembersAmount;
    @FXML private TableColumn taskStatus;
    @FXML private Button taskCreateTaskButton;
    @FXML private Text taskHeaderText;
    @FXML private DatePicker taskStartDate;
    @FXML private DatePicker taskEndDate;
    @FXML private AnchorPane createTaskAnchorPane;
    @FXML private TextField newTaskName;
    @FXML private Button newTaskCreateButton;
    @FXML private AnchorPane tableAnchorPane;
    //-----Detailed View Attributes Start-----
    @FXML private AnchorPane taskDetailsAnchorPane;
    @FXML private Text taskDetailsViewHeaderText;
    @FXML private Text taskDetailsStartWeekText;
    @FXML private Text taskDetailsEndWeekText;
    @FXML private Text taskDetailsDurationText;
    @FXML private Text taskDetailsTeamMembersText;
    @FXML private Text taskDetailsStatusText;
    @FXML private ListView taskDetailsTeamMemberList;
    @FXML private Button taskDetailsEditTaskButton;
    @FXML private Button taskDetailsDeleteTaskButton;
    @FXML private Button removeTeamMemberButton;
    @FXML private Button assignTeamMemberButton;
    @FXML private Button taskDetailsCompleteButton;
    @FXML private TableView teamMembersTable;
    @FXML private TableColumn teamMemberNameColumn;
    @FXML private TableColumn teamMemberIdColumn;
    @FXML private TableColumn teamMemberTotalTasks;
    @FXML private TableColumn teamMemberSalary;
    //-----Detailed View Attributes End-----
    //------Edit Page Attributes Start------
    @FXML private AnchorPane taskEditPageAnchorPane;
    @FXML private Text taskEditPageHeaderText;
    @FXML private TextField taskEditPageNewName;
    @FXML private DatePicker taskEditPageStartWeek;
    @FXML private DatePicker taskEditPageEndWeek;
    @FXML private Button taskEditSaveButton;
    //------Edit Page Attributes End------

    private ProjectFactory projectFactory = new ProjectFactory();
    private ProjectManagement projectManagement = projectFactory.makeProjectManagement();
    private StreamIO streamIO = projectFactory.makeStream();

    //Default method calls the load table and updates the text fields
    @FXML private void initialize(){
        tableAnchorPane.setVisible(true);
        loadTasksTable();
        setDatePicker(taskStartDate, "StartDate");
        setDatePicker(taskEndDate, "EndDate");
        createTaskAnchorPane.setVisible(false);
        taskDetailsAnchorPane.setVisible(false);
        taskEditPageAnchorPane.setVisible(false);
        updateTextFields();
        taskPage.getStylesheets().add(getClass().getResource("/se/turingturtles/css/default.css").toExternalForm());
    }

    //loads the table with all tasks
    private void loadTasksTable(){
        taskName.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskStartWeek.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
        taskDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        taskTeamMembersAmount.setCellValueFactory(new PropertyValueFactory<>("totalTeamMembers"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("completion"));
        taskEndWeek.setCellValueFactory(new PropertyValueFactory<>("endWeek"));
        taskTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        taskTableView.setPlaceholder(new Label("There are currently no tasks."));
        ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveTasks());
        taskTableView.setItems(tasks);
        taskTableView.refresh();

    }
    //Initializes the calendar for task creation given the project start and and date
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

    //Connected to createTask button - Checks the page that is loaded and calls the swaps between pages calling the update table method
    @FXML public void createNewTask(ActionEvent event){
        if (tableAnchorPane.isVisible()){
            tableAnchorPane.setVisible(false);
            createTaskAnchorPane.setVisible(true);
            taskDetailsAnchorPane.setVisible(false);
            taskEditPageAnchorPane.setVisible(false);
            taskCreateTaskButton.setText("Back");
            viewTaskButton.setVisible(false);
            loadTasksTable();
        }
        else if (taskEditPageAnchorPane.isVisible()){
            createTaskAnchorPane.setVisible(false);
            tableAnchorPane.setVisible(false);
            taskDetailsAnchorPane.setVisible(true);
            taskEditPageAnchorPane.setVisible(false);
            loadTasksTable();
        }
        else if (taskDetailsAnchorPane.isVisible()){
            createTaskAnchorPane.setVisible(false);
            tableAnchorPane.setVisible(true);
            taskDetailsAnchorPane.setVisible(false);
            taskEditPageAnchorPane.setVisible(false);
            taskCreateTaskButton.setText("Create Task");
            viewTaskButton.setVisible(true);
            loadTasksTable();
        }
        else if (createTaskAnchorPane.isVisible()){
            createTaskAnchorPane.setVisible(false);
            tableAnchorPane.setVisible(true);
            taskDetailsAnchorPane.setVisible(false);
            taskEditPageAnchorPane.setVisible(false);
            taskCreateTaskButton.setText("Create Task");
            viewTaskButton.setVisible(true);
            loadTasksTable();
        }

    }
    //Creates new task by checking for valid input and if the task with the same name already exist
    @FXML public void makeNewTask(ActionEvent event) {
        String name = newTaskName.getText();
        LocalDate taskStart = taskStartDate.getValue();
        LocalDate taskEnd = taskEndDate.getValue();
        Validator validator = projectFactory.makeValidator();
        if (validator.validateDate(taskStart, taskEnd) && validator.validateTextInput(name) ){
            boolean sameName = false;
            ArrayList<Task> tasks = (ArrayList<Task>) projectManagement.retrieveTasks();
            for (Task task : tasks) {
                if (task.getName().equals(name)) {
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
                streamIO.exportToStreamIO();
                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Success!");
                confirmationAlert.setGraphic(projectFactory.loadNode());
                confirmationAlert.setHeaderText("Successfully added task: " + name + ".");
                confirmationAlert.setContentText("The following task was added: " + "\n" + "Name: " + name + "\n" + "Start date: " + taskStart + " End date: " + taskEnd);
                confirmationAlert.showAndWait();
                loadTasksTable();
                updateTextFields();
                newTaskName.clear();
            }
        }
        else {
            if (!validator.validateTextInput(name)){
                newTaskName.clear();
                newTaskName.setPromptText("Invalid Name!");

            }
            if(!validator.validateDate(taskStart, taskEnd)){
                taskStartDate.getEditor().clear();
                taskEndDate.getEditor().clear();
                taskStartDate.setPromptText("Set Valid Date!");
                taskEndDate.setPromptText("Set Valid Date!");
            }
        }
    }

    //If a task row is selected the view task page is loaded
    public void selectTaskRow(ActionEvent event){
        Task task = (Task) taskTableView.getSelectionModel().getSelectedItem();
        if(task == null ){
            Alert selectionError = new Alert(Alert.AlertType.ERROR);
            selectionError.setTitle("Error!");
            selectionError.setGraphic(projectFactory.loadErrorNode());
            selectionError.setHeaderText("No task selected!");
            selectionError.setContentText("Please select a task from the task table.");
            selectionError.showAndWait();
        }
        else{
            loadViewTaskAnchorPane();
        }
    }

    //If the button is clicked it checks the page and loads a new page depending on the choise in the same time reloads the data
    private void loadViewTaskAnchorPane(){
        if (taskTableView.isVisible()){
            tableAnchorPane.setVisible(false);
            createTaskAnchorPane.setVisible(false);
            taskDetailsAnchorPane.setVisible(true);
            taskEditPageAnchorPane.setVisible(false);
            viewTaskButton.setVisible(false);
            taskCreateTaskButton.setText("Back");
            loadTasksTable();
        }
        else if(taskDetailsAnchorPane.isVisible()){
            taskDetailsAnchorPane.setVisible(false);
            createTaskAnchorPane.setVisible(false);
            tableAnchorPane.setVisible(true);
            taskEditPageAnchorPane.setVisible(false);
            viewTaskButton.setVisible(true);
            taskCreateTaskButton.setText("Create Task");
            loadTasksTable();
        }
        else if(taskEditPageAnchorPane.isVisible()){
            taskDetailsAnchorPane.setVisible(true);
            createTaskAnchorPane.setVisible(false);
            tableAnchorPane.setVisible(false);
            taskEditPageAnchorPane.setVisible(false);
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
        updateTables(task);
    }
    // Assigns a team member to a task method if the team member is not already assigned
    public void assignTeamMember(ActionEvent event){
        TeamMember teamMember = (TeamMember) teamMembersTable.getSelectionModel().getSelectedItem();
        Task task = projectManagement.findTask(taskDetailsViewHeaderText.getText());
        if (task != null && !task.getTeamMembers().contains(teamMember) && teamMember != null){
            projectManagement.assignTask(teamMember,task);
            updateTables(task);
            streamIO.exportToStreamIO();
            taskDetailsTeamMembersText.setText("" + task.getTotalTeamMembers());
        }
        else {
            Alert assignmentError = new Alert(Alert.AlertType.ERROR);
            assignmentError.setTitle("Error!");
            assignmentError.setGraphic(projectFactory.loadErrorNode());
            assignmentError.setHeaderText("Assignment to task failed!");
            assignmentError.setContentText("Couldn't find team member" + ",\n" + "or team member is already assigned to task!");
            assignmentError.showAndWait();
        }

    }
    //loads team member table
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
    //Creates observable list from the team member array and loads it to the table
    private void loadTaskTeamMembersList(Task task){
        ObservableList<TeamMember> teamMembers = FXCollections.observableArrayList(task.getTeamMembers());
        taskDetailsTeamMemberList.setItems(teamMembers);
    }
    //Deletes a task after showing an alert
    public void deleteTask(ActionEvent event) {
        Task task = projectManagement.findTask(taskDetailsViewHeaderText.getText());
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("You are about to delete this task");
        deleteAlert.setGraphic(projectFactory.loadErrorNode());
        deleteAlert.setHeaderText("WARNING!");
        deleteAlert.setContentText("You have selected to delete the following task: \nName: " + task.getName() + "\n\nPlease click OK, in order to proceed!");
        deleteAlert.showAndWait();
        if (deleteAlert.getResult() == ButtonType.OK) {
            projectManagement.removeTask(task);
            streamIO.exportToStreamIO();
            loadTasksTable();
            tableAnchorPane.setVisible(true);
            taskDetailsAnchorPane.setVisible(false);
            createTaskAnchorPane.setVisible(false);
            viewTaskButton.setVisible(true);
            taskCreateTaskButton.setText("Create Task");
        }
    }

    //Removes a team member from the task member list
    public void removeTeamMember(ActionEvent event){
        TeamMember teamMember = (TeamMember) taskDetailsTeamMemberList.getSelectionModel().getSelectedItem();
        Task task = projectManagement.findTask(taskDetailsViewHeaderText.getText());
        //The if-statement only executes once per run...
        if(teamMember == null ){
            Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
            deleteAlert.setGraphic(projectFactory.loadErrorNode());
            deleteAlert.setTitle("Selection missing");
            deleteAlert.setHeaderText("Hey!");
            deleteAlert.setContentText("Please select a team member to remove!");
            deleteAlert.showAndWait();
        }
        else {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("You are about to remove this member from the task");
            deleteAlert.setHeaderText("WARNING!");
            deleteAlert.setGraphic(projectFactory.loadErrorNode());
            deleteAlert.setContentText("You have selected to delete the following team member: \nName: " + teamMember.getName() + "\n\nPlease click OK, in order to proceed!");
            deleteAlert.showAndWait();
            if (deleteAlert.getResult() == ButtonType.OK) {
                task.removeTeamMember(teamMember);
                teamMember.removeTask(task);
                streamIO.exportToStreamIO();
                updateTables(task);
            }
        }
    }
    //Changes the page to edit task
    public void loadEditTaskPage(ActionEvent event){
        taskDetailsAnchorPane.setVisible(false);
        taskEditPageAnchorPane.setVisible(true);
        taskCreateTaskButton.setText("Back");
        viewTaskButton.setVisible(false);
        Task task = projectManagement.findTask(taskDetailsViewHeaderText.getText());
        taskEditPageHeaderText.setText("You are editing " + task.getName());
        taskEditPageNewName.clear();
        taskEditPageStartWeek.getEditor().clear();
        taskEditPageEndWeek.getEditor().clear();
        setDatePicker(taskEditPageStartWeek, "StartDate");
        setDatePicker(taskEditPageEndWeek, "EndDate");

    }
    //Saves the changes to the task depending of the user input. The user can change the name, the duration or all.
    public void taskEditSaveChanges(ActionEvent event){
        Task task = projectManagement.findTask(taskDetailsViewHeaderText.getText());
        String name = taskEditPageNewName.getText();
        LocalDate projectStartDate = taskEditPageStartWeek.getValue();
        LocalDate projectEndDate = taskEditPageEndWeek.getValue();
        Validator validator = projectFactory.makeValidator();
        if(validator.validateTextInput(name) && validator.validateDate(projectStartDate, projectEndDate)){
            task.setName(name);
            task.setStartDate(projectStartDate);
            task.setEndDate(projectEndDate);
            streamIO.exportToStreamIO();
            updateTables(task);
            updateTextFields();
            loadViewTaskAnchorPane();
        }
        if(validator.validateTextInput(name) && taskEditPageStartWeek.getValue() == null && taskEditPageEndWeek.getValue() == null){
            task.setName(name);
            updateTables(task);
            updateTextFields();
            loadViewTaskAnchorPane();
            streamIO.exportToStreamIO();
        }
        if(!validator.validateTextInput(name) && validator.validateDate(projectStartDate, projectEndDate)){
            task.setStartDate(projectStartDate);
            task.setEndDate(projectEndDate);
            updateTables(task);
            updateTextFields();
            loadViewTaskAnchorPane();
            streamIO.exportToStreamIO();
        }
        if(!validator.validateTextInput(name)){
                taskEditPageNewName.clear();
                taskEditPageNewName.setPromptText("Invalid name!");
        }
        if(!validator.validateDate(projectStartDate, projectEndDate)){
            taskEditPageStartWeek.getEditor().clear();
            taskEditPageEndWeek.getEditor().clear();
            taskEditPageStartWeek.setPromptText("Choose date:");
            taskEditPageEndWeek.setPromptText("Choose date:");
        }
    }
    //Changes the status of the task between completed/not completed
    public void changeStatus(){
        Task task = projectManagement.findTask(taskDetailsViewHeaderText.getText());
        if(task.getCompletion().equals("Completed")){
            task.setCompletion(false);
            streamIO.exportToStreamIO();
        }
        if(taskDetailsStatusText.getText().equals("Not Completed")){
            task.setCompletion(true);
            streamIO.exportToStreamIO();
        }
        updateTables(task);
    }

    //Calls the all the update table methods
    private void updateTables(Task task){
        loadTaskTeamMembersList(task);
        loadTeamMembersTable();
        loadTasksTable();
        taskDetailsStartWeekText.setText("" + task.getStartWeek());
        taskDetailsEndWeekText.setText("" + task.getEndWeek());
        taskDetailsDurationText.setText("" + task.getDuration());
        taskDetailsTeamMembersText.setText("" + task.getTotalTeamMembers());
        taskDetailsStatusText.setText("" + task.getCompletion());
        if(task.getCompletion().equals("Not Completed")){
            taskDetailsCompleteButton.setText("Complete Task");
        }
        else if(task.getCompletion().equals("Completed")){
            taskDetailsCompleteButton.setText("Re-Open Task");
        }
    }
    //updates the fields
    private void updateTextFields(){
        taskStartDate.getEditor().clear();
        taskEndDate.getEditor().clear();
        newTaskName.setPromptText("Enter name:");
        taskEditPageNewName.clear();
        taskEditPageStartWeek.getEditor().clear();
        taskEditPageEndWeek.getEditor().clear();
        taskEditPageNewName.setDisable(true);
        taskEditPageStartWeek.setDisable(true);
        taskEditPageEndWeek.setDisable(true);
        updateDateButton.setSelected(false);
        updateNameButton.setSelected(false);
    }

    //Radio button enables task change name
    public void enableChangeName(ActionEvent event){
        if (taskEditPageNewName.isDisable()){taskEditPageNewName.setDisable(false);}
        else{taskEditPageNewName.setDisable(true);}
    }
    //Radio button enables task change date
    public void enableChangeDate(ActionEvent event){
        if (taskEditPageStartWeek.isDisable()){
            taskEditPageStartWeek.setDisable(false);
            taskEditPageEndWeek.setDisable(false);}
        else{
            taskEditPageStartWeek.setDisable(true);
            taskEditPageEndWeek.setDisable(true);

        }


    }


}
