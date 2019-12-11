    package se.turingturtles.controllers;

    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.event.Event;
    import javafx.fxml.FXML;
    import javafx.geometry.Insets;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.Background;
    import javafx.scene.layout.BackgroundFill;
    import javafx.scene.layout.CornerRadii;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Text;
    import se.turingturtles.ProjectCalculations;
    import se.turingturtles.ProjectManagement;
    import se.turingturtles.Validator;
    import se.turingturtles.entities.Task;
    import se.turingturtles.entities.TeamMember;
    import se.turingturtles.implementations.ProjectFactory;
    import se.turingturtles.streamIO.StreamJSON;

    import java.io.IOException;

    public class TeamPageController {

        public AnchorPane teamPage;
        @FXML
        private ListView teamList;
        @FXML
        private TextField enterName;
        @FXML
        private TextField enterID;
        @FXML
        private TextField enterWage;
        @FXML
        protected Button createMemberButton;
        @FXML
        private AnchorPane newMemberPage;
        @FXML
        private TextField searchBar;
        @FXML
        private Text nameText;
        @FXML
        private Text idText;
        @FXML
        private Text wageText;
        @FXML
        private Text memberTimeSpent;
        @FXML
        private AnchorPane memberInfoPage;
        @FXML
        private AnchorPane memberEditPage;
        @FXML
        private TextField editName;
        @FXML
        private TextField editWage;
        @FXML
        private Text editMemberInfoText;
        @FXML
        private AnchorPane memberAssignTaskPage;
        @FXML
        private Text memberAssignTaskInfoText;
        @FXML
        private TableView assignTaskTable;
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
        private TableView taskTable;
        @FXML
        private TableColumn memberTaskName;
        @FXML
        private TableColumn memberTaskStartWeek;
        @FXML
        private TableColumn memberTaskDuration;
        @FXML
        private TableColumn memberTeamMembersAmount;
        @FXML
        private TableColumn memberTaskStatus;
        @FXML
        private AnchorPane landingTeamPage;
        @FXML
        private Text totalTimeSpent;
        @FXML
        private Text totalMembersAmount;
        @FXML
        private Text totalSalaries;

        ProjectFactory factory = new ProjectFactory();
        StreamJSON json = factory.makeStream();
        ProjectManagement projectManagement = factory.makeProjectManagement();
        Validator validator = factory.makeValidator();
        ProjectCalculations calculation = factory.makeProjectCalculations();
        private int lastViewedID;

        @FXML public void initialize(){
            loadTeamList();
            loadLandingPage();
        }
        public void loadLandingPage(){
            landingTeamPage.setVisible(true);
            totalSalaries.setText(String.valueOf(calculation.calculateTotalSalaries()));
            totalTimeSpent.setText(String.valueOf(projectManagement.timeSpentOnProject()));
            totalMembersAmount.setText(String.valueOf(projectManagement.getTeamMembers().size()));

        }

        public void loadTeamList() {
            ObservableList<TeamMember> members = FXCollections.observableArrayList(projectManagement.getTeamMembers());
            teamList.setItems(members);


        }

        public int findSelectedID() {
            searchBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            searchBar.clear();
            searchBar.setPromptText("Search by ID");
            Object selectedMember = teamList.getSelectionModel().getSelectedItem();
            int foundID = ((TeamMember) selectedMember).getId();
            return foundID;
        }

        public void deleteAlert(Event event) {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setTitle("Warning: Deleting member");
            deleteAlert.setHeaderText("WARNING!");
            deleteAlert.setContentText("You have selected to delete the following member: \nName: " + projectManagement.findTeamMember(lastViewedID).getName() + "\nID: " + projectManagement.findTeamMember(lastViewedID).getId() + "\n\nPlease click OK, in order to proceed!");
            deleteAlert.showAndWait();
            if (deleteAlert.getResult() == ButtonType.OK) {
                projectManagement.removeMember(projectManagement.findTeamMember(lastViewedID));
                loadTeamList();
                memberInfoPage.setVisible(false);
                loadLandingPage();
                json.exportToJSON("testData.json");
            }
        }

        public void showNewMemberCreation(ActionEvent event) {
            newMemberPage.setVisible(true);
            memberInfoPage.setVisible(false);
            memberEditPage.setVisible(false);
            memberAssignTaskPage.setVisible(false);
            landingTeamPage.setVisible(false);
        }

        public void showEditMemberPage(ActionEvent event) {
            memberEditPage.setVisible(true);
            memberInfoPage.setVisible(false);
            editMemberInfoText.setText("You are editing: " + projectManagement.findTeamMember(lastViewedID).getName() + " with ID: " + lastViewedID);
        }

        public void editMember(ActionEvent event) {
            String name = editName.getText();
            String wage = editWage.getText();

            if (validator.validateTextInput(name) && validator.validateNumericInput(wage)) {
                projectManagement.findTeamMember(lastViewedID).setName(name);
                projectManagement.findTeamMember(lastViewedID).setHourlyWage(Double.parseDouble(wage));
                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Success!");
                confirmationAlert.setHeaderText("Successfully edited team-member with ID: " + lastViewedID + ".");
                confirmationAlert.setContentText("The following information was edited: " + "\n" + "Name: " +  editName.getText() + "\n" + "Hourly wage: " + editWage.getText() + " SEK.");
                confirmationAlert.showAndWait();
                editName.clear();
                editWage.clear();
                returnToTeamPage();
                loadTeamList();
                json.exportToJSON("testData.json");
            } else {
                if (!validator.validateTextInput(name)) {
                    editName.clear();
                    editName.setPromptText("Invalid Name!");
                    //Color to be changed
                    editName.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                if (!validator.validateNumericInput(wage)) {
                    editWage.clear();
                    editWage.setPromptText("Invalid Wage!");
                    //Color to be changed
                    editWage.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }

        public void createNewMember(ActionEvent event) {
            String name = enterName.getText();
            String id = enterID.getText();
            String hourlyWage = enterWage.getText();

            if (validator.validateTextInput(name) && validator.validateNumericInput(id) && validator.validateNumericInput(hourlyWage)) {
                if(projectManagement.findTeamMember(Integer.parseInt(id)) == null) {
                    projectManagement.createMember(name, Integer.parseInt(id), Double.parseDouble(hourlyWage));
                    enterName.clear();
                    enterWage.clear();
                    enterID.clear();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("New team member creation was successful!");
                    alert.setHeaderText("You have created a new team member successfully!");
                    alert.setContentText("A new team member has been created with the following information: \nName: " + name + "\nID: " + id + "\nHourly Wage: " + hourlyWage + "\n\nPlease click OK, in order to proceed!");
                    alert.showAndWait();

                    loadTeamList();
                    json.exportToJSON("testData.json");
                }else{
                    Alert idTaken = new Alert(Alert.AlertType.ERROR);
                    idTaken.setTitle("Error!");
                    idTaken.setHeaderText("ID already assigned");
                    idTaken.setContentText("The chosen ID was already assigned to another team member, please choose another one.");
                    idTaken.showAndWait();
                }
            } else {
                //With the above if statement we make sure that in case of empty values, the fields will not show the error messages.
                if (!validator.validateTextInput(name)) {
                    enterName.clear();
                    enterName.setPromptText("Invalid Name!");
                    //Color to be changed
                    enterName.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                if (!validator.validateNumericInput(id)) {
                    enterID.clear();
                    enterID.setPromptText("Invalid ID!");
                    //Color to be changed
                    enterID.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                if (!validator.validateNumericInput(hourlyWage)) {
                    enterWage.clear();
                    enterWage.setPromptText("Invalid Wage!");
                    //Color to be changed
                    enterWage.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }

        public void loadAssignTaskList(){
            taskName.setCellValueFactory(new PropertyValueFactory<>("name"));
            taskStartWeek.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
            taskDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
            taskTeamMembersAmount.setCellValueFactory(new PropertyValueFactory<>("totalTeamMembers"));
            taskStatus.setCellValueFactory(new PropertyValueFactory<>("completion"));
            ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.retrieveTasks());
            assignTaskTable.setItems(tasks);
        }

        public void memberLoadAssignTaskList(){
            memberTaskName.setCellValueFactory(new PropertyValueFactory<>("name"));
            memberTaskStartWeek.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
            memberTaskDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
            memberTeamMembersAmount.setCellValueFactory(new PropertyValueFactory<>("totalTeamMembers"));
            memberTaskStatus.setCellValueFactory(new PropertyValueFactory<>("completion"));
            ObservableList<Task> tasks = FXCollections.observableArrayList(projectManagement.findTeamMember(lastViewedID).getTasks());
            taskTable.setItems(tasks);
        }

        public void showMemberAssignTaskPage(Event event){
            memberInfoPage.setVisible(false);
            memberAssignTaskPage.setVisible(true);
            memberAssignTaskInfoText.setText("You are assigning tasks to: " + projectManagement.findTeamMember(lastViewedID).getName() + " with ID: " + lastViewedID);
            loadAssignTaskList();
        }

        public void assignTaskToMember(Event event){
            if(assignTaskTable.getSelectionModel().getSelectedItem() == null){
                Alert selectionError = new Alert(Alert.AlertType.ERROR);
                selectionError.setTitle("Error!");
                selectionError.setHeaderText("No task selected!");
                selectionError.setContentText("Please select a task from the task list below.");
                selectionError.showAndWait();
            }else {
                if (!projectManagement.findTeamMember(lastViewedID).getTasks().contains(assignTaskTable.getSelectionModel().getSelectedItem())) {
                    projectManagement.assignTask(projectManagement.findTeamMember(lastViewedID), (Task) assignTaskTable.getSelectionModel().getSelectedItem());
                    assignTaskTable.refresh();
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success!");
                    successAlert.setHeaderText("Successful assignment!");
                    successAlert.setContentText("You have added " + projectManagement.findTeamMember(lastViewedID).getName() + " to " + ((Task) assignTaskTable.getSelectionModel().getSelectedItem()).getName() + ".");
                    successAlert.showAndWait();
                    json.exportToJSON("testData.json");
                } else {
                    Alert assignmentError = new Alert(Alert.AlertType.ERROR);
                    assignmentError.setTitle("Error!");
                    assignmentError.setHeaderText("Assignment to task failed!");
                    assignmentError.setContentText(projectManagement.findTeamMember(lastViewedID).getName() + " is already assigned to " + ((Task) assignTaskTable.getSelectionModel().getSelectedItem()).getName() + ".");
                    assignmentError.showAndWait();
                }
            }
        }
        public void loadMemberInfoPage() {
            memberAssignTaskPage.setVisible(false);
            newMemberPage.setVisible(false);
            memberInfoPage.setVisible(true);
            memberEditPage.setVisible(false);
            landingTeamPage.setVisible(false);
            memberLoadAssignTaskList();
            nameText.setText(projectManagement.findTeamMember(lastViewedID).getName());
            idText.setText(String.valueOf(projectManagement.findTeamMember(lastViewedID).getId()));
            wageText.setText(String.valueOf(projectManagement.findTeamMember(lastViewedID).getHourlyWage()));
            memberTimeSpent.setText(String.valueOf(projectManagement.findTeamMember(lastViewedID).getWeeksSpent()));


        }

        public void selectTeamMember(Event event) {
            int selectedID = findSelectedID();
            lastViewedID = selectedID;
            loadMemberInfoPage();

        }

        public void searchTeamMember(Event event) {
            if (!validator.validateNumericInput(searchBar.getText())) {
                searchBar.clear();
                searchBar.setPromptText("Invalid ID!");
                //Color to be changed
                searchBar.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                lastViewedID = Integer.parseInt(searchBar.getText());
                if (projectManagement.getTeamMembers().contains(projectManagement.findTeamMember(lastViewedID))) {
                    loadMemberInfoPage();

                } else {
                    Alert memberNotFoundError = new Alert(Alert.AlertType.ERROR);
                    memberNotFoundError.setTitle("Error!");
                    memberNotFoundError.setHeaderText("Team member not found!");
                    memberNotFoundError.setContentText("There is not a team member with the ID you were looking for.");
                    memberNotFoundError.showAndWait();
                }
                searchBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                searchBar.clear();
                searchBar.setPromptText("Search by ID");
            }

        }


        public void returnToTeamPage() {
            memberInfoPage.setVisible(false);
            newMemberPage.setVisible(false);
            memberEditPage.setVisible(false);
            memberAssignTaskPage.setVisible(false);
            loadLandingPage();
            //We clear the fields in case of incorrect values entered the last time, so that the previous values will not appear again.
            enterName.clear();
            enterWage.clear();
            enterID.clear();

            /*We set the name and the background color of the fields to the default ones, in case of invalid user input data, so they will not
            appear the next time again. */
            editName.setPromptText("Enter name");
            editName.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            editWage.setPromptText("Enter hourly wage");
            editWage.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

            enterName.setPromptText("Enter name");
            enterName.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            enterID.setPromptText("Enter ID");
            enterID.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            enterWage.setPromptText("Enter hourly wage");
            enterWage.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }