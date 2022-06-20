package com.example.habitary.model;

public class User {

    String name;
    String surname;
    String login;
    String email;
    String avatar;

    public User(){
    }

    public User(String name, String surname, String login, String email, String avatar){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
