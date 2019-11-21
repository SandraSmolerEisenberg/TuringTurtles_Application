package se.turingturtles.entities;

import java.util.ArrayList;

public class TeamMember {

    private String name;
    private int id;
    private double hourlyWage;
    private ArrayList<Task> tasks;
    private int numberOfTasks;

    public TeamMember(String name, int id, double hourlyWage){
        this.name = name;
        this.id = id;
        this.hourlyWage = hourlyWage;
        this.tasks = new ArrayList<Task>();
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getId(){
        return this.id;
    }
    public double getHourlyWage(){
        return this.hourlyWage;
    }
    public void setHourlyWage(double hourlyWage){
        this.hourlyWage = hourlyWage;
    }
    public ArrayList<Task> getTasks(){
            return tasks;
    }
    public void addTask(Task task){
        tasks.add(task);
    }
    public void removeTask(Task task){
        tasks.remove();
    }
    public int totalTasks(){
        return tasks.size();
    }

    @Override
    public String toString() {
        return "TeamMember: " + getName() + ", ID: " + getId() +
                ", Hourly wage: " + getHourlyWage() + ", Tasks: " + getTasks();
    }
}
