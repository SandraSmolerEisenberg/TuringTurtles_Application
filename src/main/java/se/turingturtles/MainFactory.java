package se.turingturtles;

import java.util.Calendar;

public class MainFactory {

    private TeamMember teamMember;
    private Project project;
    private Task task;

    public TeamMember createTeamMember(){
        return new TeamMember();
    }

    public Task createTask(){
        return new Task();
    }

    public Project createProject(String name, double budget, Calendar startDate){
        return new Project(name, budget, startDate);
    }
}
