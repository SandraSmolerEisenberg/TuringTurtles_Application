package se.turingturtles.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.turingturtles.entities.Project;
import se.turingturtles.entities.Risk;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ProjectManagement {


    void triggerCalculations();
    void createProject(String name, double budget, LocalDate projectStart, LocalDate projectEnd);
    void createTask(String name, LocalDate startDate, LocalDate endDate);
    void removeTask(Task task);
    void createMember(String name, int id, double hourlyWage);
    void removeMember(TeamMember member);
    TeamMember findTeamMember(int id);
    List<TeamMember> getTeamMembers();
    double timeSpentByTeamMember(TeamMember member);
    ArrayList<Task> retrieveMemberTasks(TeamMember member);
    void assignTask(TeamMember member, Task task);
    Task findTask(String name);
    List<Task> retrieveTasks();
    void completeTask(Task task);
    double timeSpentOnProject();
    void createRisk(String name, double impact, double probability);
    List<Risk> getProjectRisks();


    void addTime(TeamMember teamMember, Task selectedItem, double time);
}
