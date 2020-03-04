package es.uji.ei1027.majorsacasa.model;

import java.time.LocalDate;


public class Volunteer {
    private String name;
    private String dni;
    private int phoneNumber;
    private String email;
    private String pwd;
    private String hobbies;
    private LocalDate applicationDate;
    private LocalDate acceptationDate;
    private LocalDate finishDate;
    private boolean accepted;
    private LocalDate birthDate;

    public Volunteer() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setAcceptationDate(LocalDate acceptationDate) {
        this.acceptationDate = acceptationDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public String getHobbies() {
        return hobbies;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public LocalDate getAcceptationDate() {
        return acceptationDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public boolean getAccepted() {
        return accepted;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", applicationDate=" + applicationDate +
                ", acceptationDate=" + acceptationDate +
                ", finishDate=" + finishDate +
                ", accepted=" + accepted +
                ", birthDate=" + birthDate +
                '}';
    }
}
