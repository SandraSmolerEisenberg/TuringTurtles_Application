package se.turingturtles;

import java.util.List;

public class BudgetCalculations {

    private static final int UPDATE_INTERVAL = 14;
    private Project project;
    private Task task;


    public double calculateEv(){
        //Cannot calculate yet, because it is dependant on calculateCompletionBudget

        return calculateCompletedWorkPercentage()/calculateCompletionBudget();
    }


    public double calculateSV() {
        //We calculate the Schedule Variance based on calculateBCWP & calculateBCWS

        return calculateBCWP()-calculateBCWS();
    }

    public double calculateCV(){
        //We assume that actual cost of work is entirely based on the total salaries

        return project.getBudget() - calculateTotalSalaries();
    }



    public double calculateCompletedWorkPercentage(){
        List<Task> tasks = project.getTasks();
        int completedTasks = 0;

        if (tasks.size() == 0){
            return 0;
        }

        for (int i=0; i<tasks.size(); i++){
            if (tasks.getStatus()) {
                completedTasks++;
            }
        }

        return ((double)completedTasks/tasks.size());
    }

    public double calculateCompletionBudget(){
        //We can't assume how to calculate the Completion Budget yet.
    }

    public double calculateBCWP(){
        //Calculate Budget Cost of Work Performed

        return (project.getBudget()/calculateCompletedWorkPercentage());
    }

    public double calculateBCWS(){
        //Calculate Budgeted Cost of Work Scheduled
        //We are using calculateBCWP to get the remaining amount of the budget

        return project.getBudget()-calculateBCWP();
    }


    public double calculateTotalSalaries(){
        List<TeamMember> members = project.getTeamMembers();
        double totalSalary = 0;

        if (members.size() == 0) {
            return 0;
        }

        for (i=0; i<members.size(); i++){
            totalSalary += members.get(i).getSalary();
        }

        return totalSalary;
    }
}
