package se.turingturtles.entities;

import java.util.ArrayList;

public class Task {

    private String name;
    private int duration; //number of weeks
    private ArrayList<TeamMember> teamMembers;

    public Task(String name, int duration){
        this.name = name;
        this.duration = duration;
        this.teamMembers = new ArrayList<TeamMember>();
    }

    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public int getDuration(){
        return this.duration;
    }
    public void setDuration(int newDuration){
        this.duration = newDuration;
    }
    public ArrayList<TeamMember> getTeamMembers(){
        return teamMembers;
    }
    public void addTeamMember(TeamMember teamMember){
        this.teamMembers.add(teamMember);
    }
    public void removeTeamMember(TeamMember teamMember){
        this.teamMembers.remove(teamMember);
    }
    public int totalTeamMembers(){
        return teamMembers.size();
    }

    @Override
    public String toString() {
        return "Task: " + this.getName() + ", Duration: " + this.getDuration() +
                ", Members working on task: " + this.getTeamMembers();
    }
}
