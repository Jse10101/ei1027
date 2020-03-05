package es.uji.ei1027.majorsacasa.model;

import java.time.LocalDate;

public class Invoice {

    private LocalDate fecha;
    private int idNumber;
    private int amount;
    private String concept;
    private int elderlyDNI;

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

    public int getIdNumber() {
        return idNumber;
    }

    public int getAmount() {
        return amount;
    }

    public String getConcept() {
        return concept;
    }

    public int getElderlyDNI() {
        return elderlyDNI;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public void setElderlyDNI(int elderlyDNI) {
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
