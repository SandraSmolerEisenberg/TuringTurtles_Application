package se.turingturtles.implementations;

import se.turingturtles.Validator;

public class ValidatorImp implements Validator {


    @Override
    public boolean validateNumericInput(String input) {
        return input.matches("[0-9]+[,0-9]*");
    }

    @Override
    public boolean validateTextInput(String input) {
        return input.matches("[A-ZÅÄÖa-zåöä]+[ A-ZÅÄÖa-zåöä0-9]*");
    }


}

