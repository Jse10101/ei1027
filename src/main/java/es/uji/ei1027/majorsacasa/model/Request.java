package es.uji.ei1027.majorsacasa.model;

import java.time.LocalDate;

public class Request {
    private int idNumber;
    private String serviceType;
    private LocalDate creationDate;
    private boolean state;
    private LocalDate aprovedDate;
    private LocalDate rejectedDate;
    private LocalDate finishDate;
    //Claves ajenas
    private String dni_elderly;
    private int idNumber_contract;

    @Override
    public java.lang.String toString() {
        return "Request{" +
                "idNumber=" + idNumber +
                ", serviceType='" + serviceType + '\'' +
                ", creationDate=" + creationDate +
                ", state=" + state +
                ", aprovedDate=" + aprovedDate +
                ", rejectedDate=" + rejectedDate +
                ", finishDate=" + finishDate +
                ", dni_elderly='" + dni_elderly + '\'' +
                ", idNumber_contract=" + idNumber_contract +
                '}';
    }

    //GETTERS

    public int getIdNumber() {
        return idNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean getState() {
        return state;
    }

    public LocalDate getAprovedDate() {
        return aprovedDate;
    }

    public LocalDate getRejectedDate() {
        return rejectedDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public String getDni_elderly() {
        return dni_elderly;
    }

    public int getIdNumber_contract() {
        return idNumber_contract;
    }

    //SETTERS

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setAprovedDate(LocalDate aprovedDate) {
        this.aprovedDate = aprovedDate;
    }

    public void setRejectedDate(LocalDate rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setDni_elderly(String dni_elderly) {
        this.dni_elderly = dni_elderly;
    }

    public void setIdNumber_contract(int idNumber_contract) {
        this.idNumber_contract = idNumber_contract;
    }
}
