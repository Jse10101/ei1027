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
        requestInvoice.setIdNumber_request(rs.getString("idNumber_request"));
        requestInvoice.setIdNumber_invoice(rs.getString("idNumber_invoice"));
        return requestInvoice;
    }
}
