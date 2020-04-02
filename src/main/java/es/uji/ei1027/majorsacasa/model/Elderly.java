package es.uji.ei1027.majorsacasa.model;

import java.time.LocalDate;

public class Elderly {
    private String dni;
    private String name;
    private String surname;
    private String address;
    private String bankAccountNumber;
    private String userpwd;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDate dateCreation;
    private String alergies;
    private String diseases;
    private String userCAS_socialWorker;


    @Override
    public String toString() {
        return "Elderly [dni=" + dni + ", name=" + name + ", surname=" + surname + ", address=" + address
                + ", bankAccountNumber=" + bankAccountNumber + ", userpwd=" + userpwd + ", email=" + email
                + ", phoneNumber=" + phoneNumber + ", birthDate=" + birthDate + ", dateCreation=" + dateCreation
                + ", alergies=" + alergies + ", diseases=" + diseases + ", userCAS_socialWorker=" + userCAS_socialWorker
                + "]";
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getAlergies() {
        return alergies;
    }

    public void setAlergies(String alergies) {
        this.alergies = alergies;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getUserCAS_socialWorker() {
        return userCAS_socialWorker;
    }

    public void setUserCAS_socialWorker(String userCAS_socialWorker) {
        this.userCAS_socialWorker = userCAS_socialWorker;
    }


}

