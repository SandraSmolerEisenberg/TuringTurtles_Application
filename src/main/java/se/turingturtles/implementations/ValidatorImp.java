package se.turingturtles.implementations;

import se.turingturtles.Validator;

public class ValidatorImp implements Validator {

    //Valid only number input and using , for decimals
    @Override
    public boolean validateNumericInput(String input) {
        return input.matches("[0-9]+[,0-9]*");
    }
    //Validate user input It needs to start with a letter and contains letters, numbers . and ,
    @Override
    public boolean validateTextInput(String input) {
        return input.matches("[A-ZÅÄÖa-zåöä]+[ A-ZÅÄÖa-zåöä0-9,.]*");
    }


}

