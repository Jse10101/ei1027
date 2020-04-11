package es.uji.ei1027.majorsacasa.model;

public class RequestInvoice {
    private String idNumber_request;
    private String idNumber_invoice;

    public RequestInvoice() {
    }

    @Override
    public String toString() {
        return "RequestInvoice{" +
                "idNumber_request=" + idNumber_request +
                ", idNumber_invoice=" + idNumber_invoice +
                '}';
    }

    public String getIdNumber_request() {
        return idNumber_request;
    }

    public String getIdNumber_invoice() {
        return idNumber_invoice;
    }

    public void setIdNumber_request(String idNumber_request) {
        this.idNumber_request = idNumber_request;
    }

    public void setIdNumber_invoice(String idNumber_invoice) {
        this.idNumber_invoice = idNumber_invoice;
    }
}
