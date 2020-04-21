package es.uji.ei1027.majorsacasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Request {
    private String idNumber;
    private String serviceType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    private boolean state;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate aprovedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rejectedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;
    //Claves ajenas
    private String dni_elderly;
    private String idNumber_contract;

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

    public Request() {
    	
    }
    

    
    public Request(Request request) {
		this.idNumber = request.idNumber;
		this.serviceType = request.serviceType;
		this.creationDate = request.creationDate;
		this.state = request.state;
		this.aprovedDate = request.aprovedDate;
		this.rejectedDate = request.rejectedDate;
		this.finishDate = request.finishDate;
		this.dni_elderly = request.dni_elderly;
		this.idNumber_contract = request.idNumber_contract;
	}

	//GETTERS

    public String getIdNumber() {
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

    public String getIdNumber_contract() {
        return idNumber_contract;
    }

    //SETTERS

    public void setIdNumber(String idNumber) {
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

    public void setIdNumber_contract(String idNumber_contract) {
        this.idNumber_contract = idNumber_contract;
    }
}
