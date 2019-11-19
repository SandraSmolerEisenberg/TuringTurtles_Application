package se.turingturtles;

import java.util.List;

public class BudgetCalculations {

    private static final int UPDATE_INTERVAL = 14;




    public double calculateEv(double completedWork, double completionBudget){



        return 0;
    }


    public double calculateSV() {
        //We calculate the Schedule Variance based on calculateBCWP & calculateBCWS
    }

    public double calculateCV(){
        return 0;
    }


    public double calculateCompletedWorkPercentage(){
        List<Tasks> tasks = project.getTasks();
        int completedTasks = 0;

        if (tasks.size() == 0){
            return 0;
        }

        for (int i=0; i<tasks.size(); i++){
            if (tasks.getStatus()) {
                completedTasks++;
            }
        }

        return completedTasks/tasks.size();
    }

    public double calculateBCWP(){
        //Calculate Budget Cost of Work Performed
    }

    public double calculateBCWS(){
        //Calculate Budgeted Cost of Work Scheduled
    }

    public

}
