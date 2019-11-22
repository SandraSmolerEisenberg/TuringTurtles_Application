package se.turingturtles.implementations;

import se.turingturtles.ProjectManagement;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagementImp implements ProjectManagement {

    private static Project project;

    private MainFactory factory = new MainFactory();



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
        for (int i = 0; i < teamMembers.size() ; i++) {
          for (int j = 0; j < teamMembers.get(i).getTasks().size(); j++){
              if (teamMembers.get(i).getTasks().get(j).equals(task)){
                  teamMembers.get(i).getTasks().remove(j);
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

        for (int i = 0; i < tasks.size() ; i++) {
            for (int j = 0; j < tasks.get(i).getTeamMembers().size(); j++){
                if (tasks.get(i).getTeamMembers().get(j).equals(member)){
                    tasks.get(i).getTeamMembers().remove(j);
                }
            }
        }
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
    public void assignTask(TeamMember member, Task task) {
        task.getTeamMembers().add(member);
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
    public int timeSpentOnProject() {
        List<TeamMember> teamMembers = project.getTeamMembers();
        int totalTimeSpent = 0;
        for (int i = 0; i < teamMembers.size() ; i++) {
            totalTimeSpent = totalTimeSpent + teamMembers.get(i).getWeeksSpent();
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
