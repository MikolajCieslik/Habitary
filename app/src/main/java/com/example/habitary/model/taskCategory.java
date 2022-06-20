package com.example.habitary.model;

import com.google.firebase.firestore.DocumentReference;

public class taskCategory {
    String Category;
    DocumentReference userID;

    public taskCategory(){

    }

    public taskCategory(String category, DocumentReference userID){
        this.Category = category;
        this.userID = userID;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public void setUserID(DocumentReference userID) {
        this.userID = userID;
    }

    public String getCategory() {
        return Category;
    }

    public DocumentReference getUserID() {
        return userID;
    }
}
