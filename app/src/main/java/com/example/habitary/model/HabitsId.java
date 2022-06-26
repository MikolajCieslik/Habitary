package com.example.habitary.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class HabitsId {
    @Exclude
    public String HabitsId;

    public <T extends  HabitsId>T withId(@NonNull final String id){
        this.HabitsId = id;
        return (T) this;
    }
}
