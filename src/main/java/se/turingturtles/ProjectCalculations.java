package se.turingturtles;

import se.turingturtles.implementations.MainFactory;

public interface ProjectCalculations {

    double calculateEv();
    double calculateSv();
    double calculateCv();
    double calculateTotalSalaries();
    void increaseBudget(double amount);
    void decreaseBudget(double amount);

}
