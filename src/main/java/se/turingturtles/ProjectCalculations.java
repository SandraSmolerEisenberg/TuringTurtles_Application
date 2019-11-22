package se.turingturtles;

import se.turingturtles.implementations.MainFactory;

public interface ProjectCalculations {

    double calculateEarnedValue();
    double calculateScheduleVariance();
    double calculateCostVariance();
    double calculateTotalSalaries();
    void increaseBudget(double amount);
    void decreaseBudget(double amount);

}
