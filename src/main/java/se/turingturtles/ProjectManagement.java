package se.turingturtles;

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
    void addMemberToTask(TeamMember member, Task task);
    void addTaskToMember(Task task, TeamMember member);
    Task findTask(String name);
    List<Task> retrieveTasks();
    void completeTask(Task task);
    double timeSpentOnProject();


}
