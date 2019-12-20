package se.turingturtles.application;

public interface ProjectCalculations {

    double calculateEarnedValue();
    double calculateScheduleVariance();
    double calculateCostVariance();
    double calculateTotalSalaries();
    void increaseBudget(double amount);
    void decreaseBudget(double amount);

}
