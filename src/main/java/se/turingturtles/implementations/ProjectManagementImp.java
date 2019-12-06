package se.turingturtles.implementations;

import se.turingturtles.ProjectCalculations;
import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectManagementImp implements ProjectManagement {

    private final static int DAYS_OF_UPD_INTERVAL = 14;
    private final static long MILLI_SEC_PER_DAY = 86400000L;
    //Using static entity for project information to avoid database building
    private static Project project;
    private ProjectFactory factory;

    public void triggerCalculations(){
        Date today = new Date();
        long todayMilli = today.getTime();
        long nextUpdateMilli = project.getNextUpdateMilli();

        if(nextUpdateMilli == 0){
            project.setNextUpdateMilli(todayMilli + (MILLI_SEC_PER_DAY * DAYS_OF_UPD_INTERVAL));
        }
        if(todayMilli >= nextUpdateMilli) {

            ProjectCalculations calculations = factory.makeProjectCalculations();
            calculations.calculateCostVariance();
            calculations.calculateEarnedValue();
            calculations.calculateScheduleVariance();

            //calculate and set the next update day in milliseconds
            nextUpdateMilli = todayMilli + (MILLI_SEC_PER_DAY * DAYS_OF_UPD_INTERVAL);
            project.setNextUpdateMilli(nextUpdateMilli);
        }

    }

    public ProjectManagementImp() {
        this.factory =  new ProjectFactory();
    }

    @Override
    public void createProject(String name, double budget, LocalDate projectStart, LocalDate projectEnd) {
       project = factory.makeProject(name, budget, projectStart, projectEnd);
    }

    @Override
    public void createTask(String name, LocalDate startDate, LocalDate endDate) {
        project.getTasks().add(factory.makeTask(name,startDate, endDate));
    }

    // Deletes task from project and team member
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
        project.getTeamMembers().add(factory.makeTeamMember(name, id, hourlyWage));
    }

    //Deletes team member from project and task
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
    public List<TeamMember> getTeamMembers() {
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

    //Returns time spent on the project for all team members
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
        project.getRisk().add(factory.makeRisk(name, impact, probability));
    }

    @Override
    public List<Risk> getProjectRisks() {
        return project.getRisk();
    }

    public static void setProject(Project project) {
        ProjectManagementImp.project = project;
    }

    public static Project getProject() {
        return project;
    }

}
