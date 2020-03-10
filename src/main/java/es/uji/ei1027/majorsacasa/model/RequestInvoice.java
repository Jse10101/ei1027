package es.uji.ei1027.majorsacasa.model;

public class RequestInvoice {
    private int idNumber_request;
    private int idNumber_invoice;

    public RequestInvoice() {
    }

    @Override
    public String toString() {
        return "RequestInvoice{" +
                "idNumber_request=" + idNumber_request +
                ", idNumber_invoice=" + idNumber_invoice +
                '}';
    }

    public int getIdNumber_request() {
        return idNumber_request;
    }

    public int getIdNumber_invoice() {
        return idNumber_invoice;
    }

    public void setIdNumber_request(int idNumber_request) {
        this.idNumber_request = idNumber_request;
    }

    public void setIdNumber_invoice(int idNumber_invoice) {
        this.idNumber_invoice = idNumber_invoice;
    }
}
