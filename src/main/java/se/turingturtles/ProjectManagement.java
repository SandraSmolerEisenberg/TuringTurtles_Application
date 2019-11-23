package se.turingturtles;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;

import java.util.ArrayList;
import java.util.List;

public interface ProjectManagement {

    void createProject(String name, double budget, int duration);
    void createTask(String name,int startWeek, int duration);
    void removeTask(Task task);
    void createMember(String name, int id, double hourlyWage);
    void removeMember(TeamMember member);
    TeamMember findTeamMember(int id);
    List<TeamMember> retrieveTeamMembers();
    int timeSpentByTeamMember(TeamMember member);
    ArrayList<Task> retrieveMemberTasks(TeamMember member);
    void assignTask(TeamMember member, Task task);
    Task findTask(String name);
    List<Task> retrieveTasks();
    void completeTask(Task task);
    int timeSpentOnProject();
    void createRisk(String name, int impact, int probability);
    List<Risk> retrieveRisk();


}
