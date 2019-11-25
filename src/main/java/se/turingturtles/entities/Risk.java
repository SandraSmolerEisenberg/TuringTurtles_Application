package se.turingturtles.entities;

import java.util.Comparator;

public class Risk {

    private String name;
    private int impact;
    private int probability;

    public Risk(String name, int impact, int probability){
        this.name = name;
        this.impact = impact;
        this.probability = probability;
    }

    public Risk(){} //Needed for JSON-file to work

    //--------------------Getters & Setters--------------------
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
        return "Risk{" +
                "name='" + name + '\'' +
                ", impact=" + impact +
                ", probability=" + probability +
                '}';
    }


}
