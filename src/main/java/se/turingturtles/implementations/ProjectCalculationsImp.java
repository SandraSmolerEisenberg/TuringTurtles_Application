package se.turingturtles.implementations;

import se.turingturtles.ProjectCalculations;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProjectCalculationsImp implements ProjectCalculations {



    public double calculateEarnedValue(){
        return calculateCompletedWorkPercentage()*ProjectManagementImp.getProject().getBudget();
    }

    private double calculateCompletedWorkPercentage(){
        List<Task> tasks = ProjectManagementImp.getProject().getTasks();
        int completedTasks = 0;

        if (tasks.size() == 0){
            return 0;
        }

        for (int i=0; i<tasks.size(); i++){
            if (tasks.get(i).getCompletion()) {
                completedTasks++;
            }
        }

        return ((double)completedTasks/tasks.size());
    }

    public double calculateScheduleVariance() {
        //We calculate the Schedule Variance based on calculateEV & calculateBCWS
        //EV = BCWP

        return calculateEarnedValue()-calculatePlannedValue();
    }

    public double calculatePlannedValue(){
        //PlannedValue = BCWS
        Calendar calcCalendar = Calendar.getInstance(Locale.GERMANY);
        int currentWeek = calcCalendar.get(Calendar.WEEK_OF_YEAR);
        //Calculating the Planned Value by getting the current week-startweek to get a runtime duration for the project so far
        //and then we divide that with total duration to get a "percentage" which we then use to multiply with total budget to get PV
        return ((currentWeek-ProjectManagementImp.getProject().getStartWeek())/ProjectManagementImp.getProject().getDuration())*(ProjectManagementImp.getProject().getBudget());
    }


    public double calculateCostVariance(){
        //We assume that actual cost of work is entirely based on the total salaries
        return calculateEarnedValue() - calculateTotalSalaries();
    }


    public double calculateTotalSalaries(){
        List<TeamMember> members = ProjectManagementImp.getProject().getTeamMembers();
        double totalSalary = 0;

        if (members.size() == 0) {
            return 0;
        }

        for (int i=0; i<members.size(); i++){
            totalSalary += ((members.get(i).getHourlyWage())*(members.get(i).getTimeSpent()));
        }

        return totalSalary;
    }
    public void increaseBudget(double amount){}
    public void decreaseBudget(double amount){}
}
