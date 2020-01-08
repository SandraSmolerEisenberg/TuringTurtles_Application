package se.turingturtles.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@task")
public class Task implements Serializable {


    //Used for calculation of task duration
    private static final int ONE_WEEK_DAY = 1;

    private String name;
    private int startWeek;
    private int endWeek;
    private int duration; //number of weeks
    private ArrayList<TeamMember> teamMembers;
    private boolean completion;
    private int totalTeamMembers;
    private LocalDate startDate;
    private LocalDate endDate;

    public Task(String name, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.teamMembers = new ArrayList<TeamMember>();
        this.completion = false;
        this.totalTeamMembers = totalTeamMembers();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startWeek = calculateStartWeek();
        this.duration = calculateDuration();
        this.endWeek = calculateEndWeek();
    }

    public Task(){} //Needed for JSON-file to work

    //--------------------Getters & Setters--------------------
    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        setStartWeek(calculateStartWeek());
        setDuration(calculateDuration());
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        setEndWeek(calculateEndWeek());
        setDuration(calculateDuration());
    }
    public int getStartWeek(){
        return this.startWeek;
    }
    public void setStartWeek(int newStartWeek){
        this.startWeek = newStartWeek; }
    public int getDuration(){
        return this.duration;
    }
    public void setDuration(int newDuration){
        this.duration = newDuration;
    }
    public ArrayList<TeamMember> getTeamMembers(){
        return this.teamMembers;
    }
    public String getCompletion(){
        if (completion){
            return "Completed";
        }
        else return "Not Completed";
        //return this.completion;
    }
    public int getEndWeek() {
        return endWeek;
    }
    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public void setCompletion(boolean status){
        this.completion = status;
            LocalDate localDate = LocalDate.now();
            localDate = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
            setEndWeek(calculateEndWeek());
            setEndDate(localDate);
            setDuration(calculateDuration());

    }
    public int getTotalTeamMembers(){
        return teamMembers.size();
    }

    //--------------------Methods--------------------

    private int calculateEndWeek(){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return endDate.get(weekFields.weekOfWeekBasedYear());
    }
    public void addTeamMember(TeamMember teamMember){
        this.teamMembers.add(teamMember);
        setTotalTeamMembers();
    }

    public void setTeamMembers(ArrayList<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public boolean isCompletion() {
        return completion;
    }

    public void setTotalTeamMembers(int totalTeamMembers) {

        this.totalTeamMembers = totalTeamMembers;
    }

    public void removeTeamMember(TeamMember teamMember){
        this.teamMembers.remove(teamMember);
    }
    public int totalTeamMembers(){
        return this.teamMembers.size();
    }
    public void setTotalTeamMembers(){
        totalTeamMembers = totalTeamMembers();
    }
    private int calculateStartWeek(){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return startDate.get(weekFields.weekOfWeekBasedYear());
    }
    private int calculateDuration(){

        long weeks = ChronoUnit.WEEKS.between(startDate,endDate.plusDays(ONE_WEEK_DAY));
        return (int) weeks;
    }
    @Override
    public String toString() {
        return "Task: " + this.getName() + ", Duration: " + this.getDuration() +
                ", Members working on task: " + this.getTeamMembers();
    }
}
