package se.turingturtles.entities;

import java.util.ArrayList;

public class Task {

    private String name;
    private int duration; //number of weeks
    private ArrayList<TeamMember> teamMembers;
    private boolean completion;

    public Task(String name, int duration){
        this.name = name;
        this.duration = duration;
        this.teamMembers = new ArrayList<TeamMember>();
        this.completion = false;
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
        return this.teamMembers;
    }
    public void addTeamMember(TeamMember teamMember){
        this.teamMembers.add(teamMember);
    }
    public void removeTeamMember(TeamMember teamMember){
        this.teamMembers.remove(teamMember);
    }
    public int totalTeamMembers(){
        return this.teamMembers.size();
    }
    public boolean getCompletion(){
        return this.completion;
    }
    public void setCompletion(boolean status){
        this.completion = status;
    }

    @Override
    public String toString() {
        return "Task: " + this.getName() + ", Duration: " + this.getDuration() +
                ", Members working on task: " + this.getTeamMembers();
    }
}