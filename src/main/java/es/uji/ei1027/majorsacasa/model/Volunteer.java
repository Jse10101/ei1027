package es.uji.ei1027.majorsacasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class Volunteer {
    private String name;
    private String dni;
    private String phoneNumber;
    private String email;
    private String pwd;
    private String hobbies;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationDate;
    private LocalDate acceptationDate;
    private LocalDate finishDate;
    private boolean accepted;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public Volunteer() {
    }

    public Volunteer(Volunteer volunteer) {
        this.name = volunteer.name;
        this.dni = volunteer.dni;
        this.phoneNumber = volunteer.phoneNumber;
        this.email = volunteer.email;
        this.pwd = volunteer.pwd;
        this.hobbies = volunteer.hobbies;
        this.applicationDate = volunteer.applicationDate;
        this.acceptationDate = volunteer.acceptationDate;
        this.finishDate = volunteer.finishDate;
        this.accepted = volunteer.accepted;
        this.birthDate = volunteer.birthDate;
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

    public void setPhoneNumber(String phoneNumber) {
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

    public String getPhoneNumber() {
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
