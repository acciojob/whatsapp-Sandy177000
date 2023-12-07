package com.driver;

public class Group {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    private int numberOfParticipants;

    public Group(String name,  int numberOfParticipants){
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
    }

    Group(){

    }

}
