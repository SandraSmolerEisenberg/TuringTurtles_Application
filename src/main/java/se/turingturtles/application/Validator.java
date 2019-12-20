package se.turingturtles.application;

import java.time.LocalDate;

public interface Validator {

    boolean validateNumericInput(String input);
    boolean validateTextInput(String input);

    boolean validateDate(LocalDate projectStartDate, LocalDate projectEndDate);
}
