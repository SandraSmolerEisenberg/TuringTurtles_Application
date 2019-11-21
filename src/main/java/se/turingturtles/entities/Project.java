package se.turingturtles.entities;

import java.util.*;

public class Project {

    private String name;
    private double budget;
    private double duration;
    private int startWeek;
    private int startYear;
    private List<TeamMember> teamMembers;
    private List<Task> tasks;
    private Risk risk;


    public Project(String name, double budget, int duration){
        this.name = name;
        this.duration = duration;
        this.budget = budget;
        this.teamMembers = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.risk = new Risk();
        this.startWeek = assignStartWeek();
        this.startYear = assignStartYear();
    }

    public Project(){}

    private int assignStartYear() {
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.YEAR);    }

    public int assignStartWeek() {
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);

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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
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
