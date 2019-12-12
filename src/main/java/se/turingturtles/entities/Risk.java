package se.turingturtles.entities;

import java.io.Serializable;
import java.util.Comparator;

public class Risk implements Serializable {

    private String name;
    private int impact;
    private int probability;
    private int riskCalculated;


    public Risk(String name, int impact, int probability){
        this.name = name;
        this.impact = impact;
        this.probability = probability;
        this.riskCalculated = calculateRisk();
    }

    public Risk(){} //Needed for JSON-file to work

    //--------------------Getters & Setters--------------------
    public int getRiskCalculated() {
        return riskCalculated;
    }

    public void setRiskCalculated(int riskCalculated) {
        this.riskCalculated = riskCalculated;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public int getImpact(){
        return this.impact;
    }
    public void setImpact(int newImpact){
        this.impact = newImpact;
    }
    public int getProbability(){
        return this.probability;
    }
    public void setProbability(int newProbability){
        this.probability = newProbability;
    }
    //--------------------Methods--------------------
    public int calculateRisk(){
        return (this.impact * this.probability);
    }

    @Override
    public String toString() {
        return "Risk " +
                "name: " + name +
                ", impact: " + impact +
                ", probability: " + probability;
    }


}
