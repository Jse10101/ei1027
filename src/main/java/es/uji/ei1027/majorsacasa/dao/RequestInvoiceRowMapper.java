package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Availability;
import es.uji.ei1027.majorsacasa.model.RequestInvoice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestInvoiceRowMapper implements RowMapper<RequestInvoice> {
    @Override
    public RequestInvoice mapRow(ResultSet rs, int rowNum) throws SQLException {
        RequestInvoice requestInvoice = new RequestInvoice();
        requestInvoice.setIdNumber_request(rs.getInt("idNumber_request"));
        requestInvoice.setIdNumber_invoice(rs.getInt("idNumber_invoice"));
        return requestInvoice;
    }
}
