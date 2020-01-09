package se.turingturtles.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import se.turingturtles.implementations.ProjectManagementImp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@member")
public class TeamMember implements Serializable {
    //The team works 16 hours per week
    private static int hoursPerWeek = 16;

    private String name;
    private int id;
    private double hourlyWage;
    private int totalTasks;
    private ArrayList<Task> tasks;
    private HashMap<Task, Double> timeSpentOnTask;

    public TeamMember(String name, int id, double hourlyWage){
        this.name = name;
        this.id = id;
        this.hourlyWage = hourlyWage;
        this.tasks = new ArrayList<Task>();
        this.totalTasks = totalTasks();
        this.timeSpentOnTask = new HashMap<>();
    }

    public TeamMember(){} //Needed for JSON-file to work

    //--------------------Getters & Setters--------------------
    public HashMap<Task, Double> getTimeSpentOnTask() {
        return timeSpentOnTask;
    }

    public void setTimeSpentOnTask(HashMap<Task, Double> timeSpentOnTask) {
        this.timeSpentOnTask = timeSpentOnTask;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public int getId(){
        return this.id;
    }
    public double getHourlyWage(){
        return this.hourlyWage;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public static int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public static void setHoursPerWeek(int hoursPerWeek) {
        TeamMember.hoursPerWeek = hoursPerWeek;
    }

    public void setHourlyWage(double newHourlyWage){
        this.hourlyWage = newHourlyWage;
    }
    public ArrayList<Task> getTasks(){
            return tasks;
    }
    //--------------------Methods--------------------
    public void addTask(Task task){
        tasks.add(task);
        setTotalTasks(totalTasks());
        timeSpentOnTask.put(task, 0.0);
    }
    public void removeTask(Task task){

        tasks.remove(task);
        setTotalTasks(totalTasks());
        timeSpentOnTask.remove(task);
    }

    public int totalTasks(){
        return tasks.size();
    }

    public void addTime(Task task, double time){
        double round = 100.0;
        time = Math.round(round*(time)) / round;
        double timeOnTask = timeSpentOnTask.get(task);
        if (timeOnTask==0){
            timeSpentOnTask.put(task, time);
        }
        else {
            timeOnTask = timeOnTask + time;
            timeSpentOnTask.replace(task, timeOnTask);
        }
    }
    public double getTimeSpent(){
        double totalTime = 0.0;
        for(Task task : timeSpentOnTask.keySet()){
            totalTime = totalTime + timeSpentOnTask.get(task);
        }
        double round = 100.0;
        totalTime = Math.round(round*(totalTime)) / round;
        return totalTime;
    }

    @Override
    public String toString() {
        return this.getName() + "(ID: " + this.getId() + ")";
    }

}
