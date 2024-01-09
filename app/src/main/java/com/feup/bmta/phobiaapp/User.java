package com.feup.bmta.phobiaapp;

public class User {
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private String idCardNumber;
    private String username;

    private long id;

    // Construtor
    public User(String fullName, String dateOfBirth, String gender, String idCardNumber, String username) {
        this.id= id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.idCardNumber = idCardNumber;
        this.username = username;
    }

    // Getters
    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public String getUsername() {
        return username;
    }

    // Setters (opcional, dependendo se você precisa deles)
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
