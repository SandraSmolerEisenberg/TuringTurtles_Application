package se.turingturtles.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@risk")
public class Risk implements Serializable {

    private String name;
    private double impact;
    private double probability;
    private double riskCalculated;


    public Risk(String name, double impact, double probability){
        this.name = name;
        this.impact = impact;
        this.probability = probability;
        this.riskCalculated = calculateRisk();
    }

    public Risk(){} //Needed for JSON-file to work

    //--------------------Getters & Setters--------------------
    public double getRiskCalculated() {
        return riskCalculated;
    }

    public void setRiskCalculated(double riskCalculated) {
        this.riskCalculated = riskCalculated;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public double getImpact(){
        return this.impact;
    }
    public void setImpact(double newImpact){
        this.impact = newImpact;
    }
    public double getProbability(){
        return this.probability;
    }
    public void setProbability(double newProbability){
        this.probability = newProbability;
    }
    //--------------------Methods--------------------
    public double calculateRisk(){
        double round = 100.0;
        double risk = Math.round(round*(this.impact * this.probability)) / round;
        setRiskCalculated(risk);
        return risk;
    }

    @Override
    public String toString() {
        return "Risk " +
                "name: " + name +
                ", impact: " + impact +
                ", probability: " + probability;
    }


}
