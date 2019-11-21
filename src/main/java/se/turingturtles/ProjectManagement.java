package se.turingturtles;

import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;

import java.util.ArrayList;
import java.util.List;

public interface ProjectManagement {

    void createProject(String name, double budget, int duration);
    void createTask();
    void removeTask(Task task);
    void createMember();
    void removeMember(TeamMember member);
    TeamMember findTeamMember(int id);
    List<TeamMember> retrieveTeamMembers();
    double timeSpentByTeamMember(TeamMember member);
    ArrayList<Task> retrieveMemberTasks(TeamMember member);
    void addMemberToTask(TeamMember member, Task task);
    void addTaskToMember(Task task, TeamMember member);
    Task findTask(int id);
    List<Task> retrieveTasks();
    void completeTask(Task task);
    double timeSpentOnProject();


}
