package com.example.habitary.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class Task {

    String name;
    String category;
    String description;
    DocumentReference userID;
    Boolean finishFlag;
    Timestamp startDate;
    Timestamp alertDate;
    Timestamp endDate;

    public Task(){
    }

    public Task(String name, String category, String description, DocumentReference idUser, Timestamp startDate, Timestamp alertDate, Timestamp endDate){
        this.name = name;
        this.category = category;
        this.description = description;
        this.userID = idUser;
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

    public DocumentReference getUserID() {
        return userID;
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

    public void setUserID(DocumentReference userID) {
        this.userID = userID;
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
