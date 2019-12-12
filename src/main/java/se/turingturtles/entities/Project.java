package se.turingturtles.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;

public class Project implements Serializable {

    private static final int NUMBER_OF_WEEKS_IN_A_YEAR = 52;
    private String name;
    private double budget;
    private int duration;    //In weeks
    private int startWeek;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private int endWeek;
    private int startYear;
    private List<TeamMember> teamMembers;
    private List<Task> tasks;
    private List<Risk> risk;
    private long nextUpdateMilli;
    private double costVariance;
    private double earnedValue;
    private double scheduleVariance;



    public Project(String name, double budget, LocalDate projectStart, LocalDate projectEnd){
        this.name = name;
        this.budget = budget;
        this.teamMembers = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.risk = new ArrayList<>();
        this.startWeek = assignStartWeek(projectStart);
        this.startYear = assignStartYear(projectStart);
        this.nextUpdateMilli = 0;
        this.projectStartDate = projectStart;
        this.projectEndDate = projectEnd;
        this.duration = calculateDuration();
        this.endWeek = calculateEndWeek();
        this.costVariance = 0;
        this.earnedValue = 0;
        this.scheduleVariance = 0;
    }


    public Project(){} //Needed for JSON-file to work

    private int calculateEndWeek() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return projectEndDate.get(weekFields.weekOfWeekBasedYear());
    }

    private int calculateDuration(){
        long weeks = ChronoUnit.WEEKS.between(projectStartDate,projectEndDate);
        return (int) weeks;
    }
    // Assign the project start week and Year
    private int assignStartYear(LocalDate projectStart) {
        return projectStart.getYear();    }

    private int assignStartWeek(LocalDate projectStart) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return projectStart.get(weekFields.weekOfWeekBasedYear());
    }

    public static int getNumberOfWeeksInAYear() {
        return NUMBER_OF_WEEKS_IN_A_YEAR;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
        setStartWeek(assignStartWeek(projectStartDate));
        setDuration(calculateDuration());
    }

    public LocalDate getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(LocalDate projectEndDate) {
        this.projectEndDate = projectEndDate;
        setEndWeek(calculateEndWeek());
        setDuration(calculateDuration());
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

    public double getCostVariance() {
        return costVariance;
    }

    public void setCostVariance(double costVariance) {
        this.costVariance = costVariance;
    }

    public double getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(double earnedValue) {
        this.earnedValue = earnedValue;
    }

    public double getScheduleVariance() {
        return scheduleVariance;
    }

    public void setScheduleVariance(double scheduleVariance) {
        this.scheduleVariance = scheduleVariance;
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
