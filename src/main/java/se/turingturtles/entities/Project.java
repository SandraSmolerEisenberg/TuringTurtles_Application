package se.turingturtles.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@project")
public class Project implements Serializable {

    //Used for calculation of project duration
    private static final int ONE_WEEK_DAY = 1;
    private String name;
    private double budget;
    private int duration;    //In weeks
    private int startWeek;
    private int endWeek;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
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
        this.nextUpdateMilli = 0;
        this.projectStartDate = projectStart;
        this.projectEndDate = projectEnd;
        this.duration = calculateDuration();
        this.endWeek = calculateEndWeek(projectEnd);
        this.costVariance = 0;
        this.earnedValue = 0;
        this.scheduleVariance = 0;
    }

    public Project(){} //Needed for JSON-file to work

//--------Methods-------------------------
    private int calculateEndWeek(LocalDate projectEndDate) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return projectEndDate.get(weekFields.weekOfWeekBasedYear());
    }

    private int calculateDuration(){
        projectEndDate = projectEndDate.plusDays(ONE_WEEK_DAY);
        long weeks = ChronoUnit.WEEKS.between(projectStartDate,projectEndDate);
        return (int) weeks;
    }
    // Assign the project start week
    private int assignStartWeek(LocalDate projectStart) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return projectStart.get(weekFields.weekOfWeekBasedYear());
    }

//--------Getters and Setters----------------
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
        setEndWeek(calculateEndWeek(projectEndDate));
        setDuration(calculateDuration());
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
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
        return "Project " +
                "name: " + name  +
                ", budget: " + budget +
                ", duration: " + duration;
    }

}
