package se.turingturtles;

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

    public Project createProject(String name, double budget){
        return new Project(name, budget);
    }
}
