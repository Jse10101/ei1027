package es.uji.ei1027.majorsacasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Availability {

    private LocalDate fecha;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalDate beginingHour;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalDate endingHour;
    private boolean stateAvailability;
    private String dni_volunteer;
    private String dni_elderly;


    public Availability() {
    }

    @Override
    public String toString() {
        return "Availability{" +
                "fecha=" + fecha +
                ", beginingHour=" + beginingHour +
                ", endinggHour=" + endingHour +
                ", stateAvailability=" + stateAvailability +
                ", dni_volunteer='" + dni_volunteer + '\'' +
                ", dni_elderly='" + dni_elderly + '\'' +
                '}';
    }

    public String getDni_volunteer() {
        return dni_volunteer;
    }

    public void setDni_volunteer(String dni_volunteer) {
        this.dni_volunteer = dni_volunteer;
    }

    public String getDni_elderly() {
        return dni_elderly;
    }

    public void setDni_elderly(String dni_elderly) {
        this.dni_elderly = dni_elderly;
    }


    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate date) {
        this.fecha = date;
    }

    public LocalDate getBeginingHour() {
        return beginingHour;
    }

    public void setBeginingHour(LocalDate beginningHour) {
        this.beginingHour = beginningHour;
    }

    public LocalDate getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(LocalDate endinggHour) {
        this.endingHour = endinggHour;
    }

    public boolean getStateAvailability() {
        return stateAvailability;
    }

    public void setStateAvailability(boolean stateAvailable) {
        this.stateAvailability = stateAvailable;
    }
}
