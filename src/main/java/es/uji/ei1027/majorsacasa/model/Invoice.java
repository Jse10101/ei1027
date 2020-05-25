package es.uji.ei1027.majorsacasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Invoice {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private String idNumber;
    private int amount;
    private String concept;
    private String elderlyDNI;

//    public Invoice(LocalDate fecha, int idNumber, int amount, String concept, int elderlyDNI) {
//        this.fecha = fecha;
//        this.idNumber = idNumber;
//        this.amount = amount;
//        this.concept = concept;
//        this.elderlyDNI = elderlyDNI;
//    }

    public Invoice(){

    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public int getAmount() {
        return amount;
    }

    public String getConcept() {
        return concept;
    }

    public String getElderlyDNI() {
        return elderlyDNI;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public void setElderlyDNI(String elderlyDNI) {
        this.elderlyDNI = elderlyDNI;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "fecha=" + fecha +
                ", idNumber=" + idNumber +
                ", amount=" + amount +
                ", concept='" + concept + '\'' +
                ", elderlyDNI=" + elderlyDNI +
                '}';
    }


}
