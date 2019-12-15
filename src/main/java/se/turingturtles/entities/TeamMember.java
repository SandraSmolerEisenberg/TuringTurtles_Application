package se.turingturtles.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import se.turingturtles.implementations.ProjectManagementImp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@member")
public class TeamMember implements Serializable {
    //The team works 16 hours per week
    private static int hoursPerWeek = 16;

    private String name;
    private int id;
    private double hourlyWage;
    private int totalTasks;
    private ArrayList<Task> tasks;

    public TeamMember(String name, int id, double hourlyWage){
        this.name = name;
        this.id = id;
        this.hourlyWage = hourlyWage;
        this.tasks = new ArrayList<Task>();
        this.totalTasks = totalTasks();
    }

    public TeamMember(){} //Needed for JSON-file to work

    //--------------------Getters & Setters--------------------
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
    }
    public void removeTask(Task task){

        tasks.remove(task);
        setTotalTasks(totalTasks());
    }
    public int totalTasks(){
        return tasks.size();
    }
    public int getWeeksSpent(){
        int currentWeek = assignStartWeek();
        int weeksSpent = 0;
        LocalDate currentDate = LocalDate.now();
        for(Task obj : tasks){
            if(currentDate.isBefore(obj.getEndDate()) && currentDate.isAfter(obj.getStartDate())) {
                weeksSpent += (currentWeek - obj.getStartWeek());
            }else if(currentDate.isAfter(obj.getEndDate())){
                weeksSpent += obj.getDuration();
            }
        }
        return weeksSpent;
    }

    private int assignStartWeek(){
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public int getTimeSpent(){

        return (getWeeksSpent() * hoursPerWeek);
    }

    @Override
    public String toString() {
        return this.getName() + "(ID: " + this.getId() + ")";
    }

}
