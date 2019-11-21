package se.turingturtles.implementations;

import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;

import java.util.ArrayList;
import java.util.List;

public class ProjectManagementImp implements ProjectManagement {

    private static Project project;

    private MainFactory factory = new MainFactory();

    @Override
    public void createProject(String name, double budget, int duration) {
       project = factory.createProject(name, budget,duration);
    }

    @Override
    public void createTask(String name,int startWeek, int duration) {
        project.getTasks().add(factory.createTask(name,startWeek, duration));
    }

    @Override
    public void removeTask(Task task) {
        project.getTasks().remove(task);
    }

    @Override
    public void createMember(String name, int id, double hourlyWage) {
        project.getTeamMembers().add(factory.createTeamMember(name, id, hourlyWage));
    }

    @Override
    public void removeMember(TeamMember member) {
        project.getTeamMembers().remove(member);
    }

    @Override
    public TeamMember findTeamMember(int id)
    {
        List<TeamMember> teamMembers = project.getTeamMembers();
        for (int i = 0; i < teamMembers.size() ; i++) {
            if (teamMembers.get(i).getId() == id){
                return teamMembers.get(i);
            }
        }
        return null;
    }

    @Override
    public List<TeamMember> retrieveTeamMembers() {
        return project.getTeamMembers();
    }

    @Override
    public int timeSpentByTeamMember(TeamMember member) {
        return member.getWeeksSpent();
    }

    @Override
    public ArrayList<Task> retrieveMemberTasks(TeamMember member) {
        return member.getTasks();
    }

    @Override
    public void addMemberToTask(TeamMember member, Task task) {
        task.getTeamMembers().add(member);
    }

    @Override
    public void addTaskToMember(Task task, TeamMember member) {
        member.getTasks().add(task);
    }

    @Override
    public Task findTask(String name) {
        List<Task> tasks = project.getTasks();
        for (int i = 0; i < tasks.size() ; i++) {
            if (tasks.get(i).getName().equals(name)){
                return tasks.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Task> retrieveTasks() {
        return project.getTasks();
    }

    @Override
    public void completeTask(Task task) {
            task.setCompletion(true);
    }

    @Override
    public double timeSpentOnProject() {
        List<TeamMember> teamMembers = project.getTeamMembers();
        int totalTimeSpent = 0;
        for (int i = 0; i < teamMembers.size() ; i++) {
            totalTimeSpent = totalTimeSpent + teamMembers.get(i).getWeeksSpent();
        }
        return totalTimeSpent;
    }

    public static Project getProject() {
        return project;
    }
}
