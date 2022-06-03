package com.example.habitary.model;

import com.google.firebase.Timestamp;

public class Habit {
    String name;
    String description;
    String frequency;//nie wiem jaki typ
    Integer streakCounter;
    Boolean finishFlag;
    Timestamp alertDate;
    String idUser;//nie wiem czy potrzebne

    public Habit(){
    }

    public Habit(String name, String description, String frequency, Timestamp alertDate, String idUser){
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.streakCounter = 0;
        this.finishFlag = false;
        this.alertDate = alertDate;
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFrequency() {
        return frequency;
    }

    public Integer getStreakCounter() {
        return streakCounter;
    }

    public Boolean getFinishFlag() {
        return finishFlag;
    }

    public Timestamp getAlertDate() {
        return alertDate;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setStreakCounter(Integer streakCounter) {
        this.streakCounter = streakCounter;
    }

    public void setFinishFlag(Boolean finishFlag) {
        this.finishFlag = finishFlag;
    }

    public void setAlertDate(Timestamp alertDate) {
        this.alertDate = alertDate;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
