package se.turingturtles.implementations;

import se.turingturtles.ProjectCalculations;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.entities.Project;

import java.util.List;

public class ProjectCalculationsImp implements ProjectCalculations {



    public double calculateEarnedValue(){
        //Cannot calculate yet, because it is dependant on calculateCompletionBudget

        return calculateCompletedWorkPercentage()/calculateCompletionBudget();
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

    private double calculateCompletionBudget(){
        //We can't assume how to calculate the Completion Budget yet.
        return 0;
    }


    public double calculateScheduleVariance() {
        //We calculate the Schedule Variance based on calculateBCWP & calculateBCWS

        return calculateBCWP()-calculateBCWS();
    }

    private double calculateBCWP(){
        //Calculate Budget Cost of Work Performed

        return (ProjectManagementImp.getProject().getBudget()/calculateCompletedWorkPercentage());
    }

    private double calculateBCWS(){
        //Calculate Budgeted Cost of Work Scheduled
        //We are using calculateBCWP to get the remaining amount of the budget

        return ProjectManagementImp.getProject().getBudget()-calculateBCWP();
    }


    public double calculateCostVariance(){
        //We assume that actual cost of work is entirely based on the total salaries

        return ProjectManagementImp.getProject().getBudget() - calculateTotalSalaries();
    }


    public double calculateTotalSalaries(){
        List<TeamMember> members = ProjectManagementImp.getProject().getTeamMembers();
        double totalSalary = 0;

        if (members.size() == 0) {
            return 0;
        }

        for (int i=0; i<members.size(); i++){
            totalSalary += members.get(i).getHourlyWage();
        }

        return totalSalary;
    }
    public void increaseBudget(double amount){}
    public void decreaseBudget(double amount){}
}
