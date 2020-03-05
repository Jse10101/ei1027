package es.uji.ei1027.majorsacasa.model;

import java.time.LocalDate;

public class Contract {
	int idNumber;
	LocalDate dateBegining;
	LocalDate dateEnding;
	String serviceType;
	int price;
	String cif_company;
	
	public int getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	public LocalDate getDateBegining() {
		return dateBegining;
	}

	public void setDateBegining(LocalDate dateBegining) {
		this.dateBegining = dateBegining;
	}

	public LocalDate getDateEnding() {
		return dateEnding;
	}

	public void setDateEnding(LocalDate dateEnding) {
		this.dateEnding = dateEnding;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCif_company() {
		return cif_company;
	}

	public void setCif_company(String cif_company) {
		this.cif_company = cif_company;
	}

	@Override
	public String toString() {
		return "Contract [idNumber=" + idNumber + ", dateBegining=" + dateBegining + ", dateEnding=" + dateEnding
				+ ", serviceType=" + serviceType + ", price=" + price + ", cif_company=" + cif_company + "]";
	}
	
	
}
