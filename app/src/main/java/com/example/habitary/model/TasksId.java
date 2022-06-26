package com.example.habitary.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class TasksId {
    @Exclude
    public String TasksId;

    public <T extends  TasksId>T withId(@NonNull final String id){
        this.TasksId = id;
        return (T) this;
    }
}
