package se.turingturtles.implementations;

import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.TeamMember;

import java.util.ArrayList;
import java.util.List;

public class ProjectManagementImp implements ProjectManagement {

    @Override
    public void createProject(String name, double budget, int duration) {

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
    public ArrayList<Task> retrieveMemberTasks(TeamMember member) {
        return null;
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
    public double timeSpentOnProject() {
        return 0;
    }
}
