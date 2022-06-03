package com.example.habitary.model;

public class User {

    String name;
    String surname;
    String login;
    String email;
    String password;
    String avatar;
    Short accessLevel;

    public User(){
    }

    public User(String name, String surname, String login, String email, String password, String avatar){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.accessLevel = 1;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public Short getAccessLevel() {
        return accessLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    //nie wiem czy konieczne
    public void setAccessLevel(Short accessLevel) {
        this.accessLevel = accessLevel;
    }
}
