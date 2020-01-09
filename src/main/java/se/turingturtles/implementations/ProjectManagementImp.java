package se.turingturtles.implementations;

import se.turingturtles.application.ProjectCalculations;
import se.turingturtles.application.ProjectManagement;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagementImp implements ProjectManagement, Serializable {

    private final static int DAYS_OF_UPD_INTERVAL = 14;
    private final static long MILLI_SEC_PER_DAY = 86400000L;
    //Using static entity for project information to avoid database building
    private static Project project;
    private ProjectFactory factory;

    public ProjectManagementImp() {
        this.factory =  new ProjectFactory();
    }

    public void triggerCalculations(){
        LocalDate today = project.getProjectStartDate();
        long todayMilli = today.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        long nextUpdateMilli = project.getNextUpdateMilli();

        if(nextUpdateMilli == 0){
            project.setNextUpdateMilli(todayMilli + (MILLI_SEC_PER_DAY * DAYS_OF_UPD_INTERVAL));
        }
        else if(todayMilli >= nextUpdateMilli) {

            ProjectCalculations calculations = factory.makeProjectCalculations();
            project.setCostVariance(Math.round(calculations.calculateCostVariance()));
            project.setEarnedValue(Math.round(calculations.calculateEarnedValue()));
            project.setScheduleVariance(Math.round(calculations.calculateScheduleVariance()));

            //calculate and set the next update day in milliseconds
            nextUpdateMilli = todayMilli + (MILLI_SEC_PER_DAY * DAYS_OF_UPD_INTERVAL);
            project.setNextUpdateMilli(nextUpdateMilli);
        }

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
    public double timeSpentByTeamMember(TeamMember member) {
        return member.getTimeSpent();
    }

    @Override
    public ArrayList<Task> retrieveMemberTasks(TeamMember member) {
        return member.getTasks();
    }

    @Override
    public void assignTask(TeamMember member, Task task) {
        task.addTeamMember(member);
        member.addTask(task);
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
    public double timeSpentOnProject() {
        List<TeamMember> teamMembers = project.getTeamMembers();
        double totalTimeSpent = 0;
        for (TeamMember teamMember : teamMembers) {
            totalTimeSpent = totalTimeSpent + teamMember.getTimeSpent();
        }
        return totalTimeSpent;
    }

    @Override
    public void createRisk(String name, double impact, double probability) {
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

    public void addTime(TeamMember teamMember, Task task, double time){
        teamMember.addTime(task,time);
    }


}
