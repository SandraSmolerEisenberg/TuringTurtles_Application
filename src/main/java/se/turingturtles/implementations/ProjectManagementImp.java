package se.turingturtles.implementations;

import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectManagementImp implements ProjectManagement {

    private final static int DAYS_OF_UPD_INTERVAL = 14;
    private final static long MILLI_SEC_PER_DAY = 86400000L;

    private static Project project;

    private ProjectFactory factory;

    public void triggerCalculations(){
        Date today = new Date();
        long todayMilli = today.getTime();
        long nextUpdateMilli = Project.getNextUpdateMilli();

        if(nextUpdateMilli == 0){
            nextUpdateMilli = today.getTime();
        }
        if(todayMilli >= nextUpdateMilli) {

            //Add calculations here

            //calculate and set the next update day in milliseconds
            nextUpdateMilli = todayMilli + (MILLI_SEC_PER_DAY * DAYS_OF_UPD_INTERVAL);
            Project.setNextUpdateMilli(nextUpdateMilli);
        }

    }

    public ProjectManagementImp() {
        this.factory =  new ProjectFactory();
    }

    @Override
    public void createProject(String name, double budget, int duration) {
       project = factory.createProject(name, budget, duration);
    }

    @Override
    public void createTask(String name,int startWeek, int duration) {
        project.getTasks().add(factory.createTask(name,startWeek, duration));
    }

    @Override
    public void removeTask(Task task) {

        project.getTasks().remove(task);
        List<TeamMember> teamMembers = project.getTeamMembers();
        for (TeamMember teamMember : teamMembers) {
            for (int j = 0; j < teamMember.getTasks().size(); j++) {
                if (teamMember.getTasks().get(j).equals(task)) {
                    teamMember.getTasks().remove(j);
                }
            }
        }
    }

    @Override
    public void createMember(String name, int id, double hourlyWage) {
        project.getTeamMembers().add(factory.createTeamMember(name, id, hourlyWage));
    }

    @Override
    public void removeMember(TeamMember member) {
        project.getTeamMembers().remove(member);
        List<Task> tasks = project.getTasks();

        for (Task task : tasks) {
            for (int j = 0; j < task.getTeamMembers().size(); j++) {
                if (task.getTeamMembers().get(j).equals(member)) {
                    task.getTeamMembers().remove(j);
                }
            }
        }
    }

    @Override
    public TeamMember findTeamMember(int id)
    {
        List<TeamMember> teamMembers = project.getTeamMembers();
        for (TeamMember teamMember : teamMembers) {
            if (teamMember.getId() == id) {
                return teamMember;
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
    public void assignTask(TeamMember member, Task task) {
        task.getTeamMembers().add(member);
        member.getTasks().add(task);
    }

    @Override
    public Task findTask(String name) {
        List<Task> tasks = project.getTasks();
        for (Task task : tasks) {
            if (task.getName().equals(name)) {
                return task;
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
    public int timeSpentOnProject() {
        List<TeamMember> teamMembers = project.getTeamMembers();
        int totalTimeSpent = 0;
        for (TeamMember teamMember : teamMembers) {
            totalTimeSpent = totalTimeSpent + teamMember.getWeeksSpent();
        }
        return totalTimeSpent;
    }

    @Override
    public void createRisk(String name, int impact, int probability) {
        project.getRisk().add(factory.createRisk(name, impact, probability));
    }

    @Override
    public List<Risk> retrieveRisk() {
        return project.getRisk();
    }

    public static void setProject(Project project) {
        ProjectManagementImp.project = project;
    }

    public static Project getProject() {
        return project;
    }

}
