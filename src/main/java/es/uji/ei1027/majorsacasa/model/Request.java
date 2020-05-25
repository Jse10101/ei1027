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
    private String comentario;
    
	@Override
	public String toString() {
		return "Request [idNumber=" + idNumber + ", serviceType=" + serviceType + ", creationDate=" + creationDate
				+ ", state=" + state + ", aprovedDate=" + aprovedDate + ", rejectedDate=" + rejectedDate
				+ ", finishDate=" + finishDate + ", dni_elderly=" + dni_elderly + ", idNumber_contract="
				+ idNumber_contract + ", comentario=" + comentario + "]";
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public LocalDate getAprovedDate() {
		return aprovedDate;
	}
	public void setAprovedDate(LocalDate aprovedDate) {
		this.aprovedDate = aprovedDate;
	}
	public LocalDate getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(LocalDate rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	public LocalDate getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}
	public String getDni_elderly() {
		return dni_elderly;
	}
	public void setDni_elderly(String dni_elderly) {
		this.dni_elderly = dni_elderly;
	}
	public String getIdNumber_contract() {
		return idNumber_contract;
	}
	public void setIdNumber_contract(String idNumber_contract) {
		this.idNumber_contract = idNumber_contract;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

    
}
