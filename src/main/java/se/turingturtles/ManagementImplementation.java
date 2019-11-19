package se.turingturtles;

import java.util.Calendar;
import java.util.List;

public class ManagementImplementation implements ProjectManagement{

    @Override
    public void createProject(String name, double budget, Calendar startDate) {

    }

    @Override
    public void createTask() {

    }

    @Override
    public void removeTask(Task task) {

    }

    @Override
    public void createMember() {

    }

    @Override
    public void removeMember(TeamMember member) {

    }

    @Override
    public TeamMember findTeamMember(int id) {
        return null;
    }

    @Override
    public List<TeamMember> retrieveTeamMembers() {
        return null;
    }

    @Override
    public double timeSpentByTeamMember(TeamMember member) {
        return 0;
    }

    @Override
    public void addMemberToTask(TeamMember member, Task task) {

    }

    @Override
    public void addTaskToMember(Task task, TeamMember member) {

    }

    @Override
    public Task findTask(int id) {
        return null;
    }

    @Override
    public List<Task> retrieveTasks() {
        return null;
    }

    @Override
    public void completeTask(Task task) {

    }

    @Override
    public void increaseBudget(double amount) {

    }

    @Override
    public void decreaseBudget(double amount) {

    }

    @Override
    public double timeSpentOnProject() {
        return 0;
    }
}
