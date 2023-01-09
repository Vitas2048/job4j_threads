package ru.job4j.mail;

public class User {
    private String userName;

    private String email;

    public User(String userName, String email) {
        this.email = email;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
