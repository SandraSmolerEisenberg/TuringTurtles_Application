package se.turingturtles;

import java.util.Calendar;
import java.util.List;

public interface ProjectManagement {

    void createProject(String name, double budget, Calendar startDate);
    void createTask();
    void removeTask(Task task);
    void createMember();
    void removeMember(TeamMember member);
    TeamMember findTeamMember(int id);
    List<TeamMember> retrieveTeamMembers();
    double timeSpentByTeamMember(TeamMember member);
    void addMemberToTask(TeamMember member, Task task);
    void addTaskToMember(Task task, TeamMember member);
    Task findTask(int id);
    List<Task> retrieveTasks();
    void completeTask(Task task);
    void increaseBudget(double amount);
    void decreaseBudget(double amount);
    double timeSpentOnProject();


}
