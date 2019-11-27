package se.turingturtles.entities;

import java.util.*;

public class Project {

    private String name;
    private double budget;
    private int duration;    //In weeks
    private int startWeek;
    private int startYear;
    private List<TeamMember> teamMembers;
    private List<Task> tasks;
    private List<Risk> risk;
    final int DAYS_OF_UPD_INTERVAL = 14;
    private long nextUpdateMilli;


    public Project(String name, double budget, int duration){
        this.name = name;
        this.duration = duration;
        this.budget = budget;
        this.teamMembers = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.risk = new ArrayList<>();
        this.startWeek = assignStartWeek();
        this.startYear = assignStartYear();
        this.nextUpdateMilli = 0;
    }

    public Project(){} //Needed for JSON-file to work

    // Assign the project start week and Year
    int assignStartYear() {
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.YEAR);    }

    int assignStartWeek() {
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public void triggerCalculations(){
        Date today = new Date();

        long todayMilli = today.getTime();
        if(nextUpdateMilli == 0){
            nextUpdateMilli = today.getTime();
        }
        if(todayMilli >= nextUpdateMilli) {

            //Calculation method calls goes here

            //set the next update day in millisec
            long millisecPerDay = 86400000L;
            nextUpdateMilli = todayMilli + (millisecPerDay * DAYS_OF_UPD_INTERVAL);
        }

    }


    public int getStartWeek() {
        return startWeek;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getDuration() {
        return duration;
    }
    public long getNextUpdateMilli() {
        return nextUpdateMilli;
    }

    public void setNextUpdateMilli(long nextUpdateMilli) {
        this.nextUpdateMilli = nextUpdateMilli;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Risk> getRisk() {
        return risk;
    }

    public void setRisk(List<Risk> risk) {
        this.risk = risk;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", budget=" + budget +
                ", duration=" + duration +
                '}';
    }

}
