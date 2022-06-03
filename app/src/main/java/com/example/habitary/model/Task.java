package com.example.habitary.model;

import com.google.firebase.Timestamp;

public class Task {

    String name;
    String category;
    String description;
    String idUser;//nie wiem czy potrzebne
    Boolean finishFlag;
    Timestamp startDate;
    Timestamp alertDate;
    Timestamp endDate;

    public Task(){
    }

    public Task(String name, String category, String description, String idUser, Timestamp startDate, Timestamp alertDate, Timestamp endDate){
        this.name = name;
        this.category = category;
        this.description = description;
        this.idUser = idUser;
        this.finishFlag = false;
        this.startDate = startDate;
        this.alertDate = alertDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getIdUser() {
        return idUser;
    }

    public Boolean getFinishFlag() {
        return finishFlag;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getAlertDate() {
        return alertDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setFinishFlag(Boolean finishFlag) {
        this.finishFlag = finishFlag;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public void setAlertDate(Timestamp alertDate) {
        this.alertDate = alertDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
