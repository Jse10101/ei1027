package es.uji.ei1027.majorsacasa.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.majorsacasa.model.Request;

public class RequestRowMapper implements RowMapper<Request> {

    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();
        request.setIdNumber(rs.getString("idNumber"));
        request.setServiceType(rs.getString("serviceType"));
        request.setCreationDate(rs.getDate("creationDate").toLocalDate());
        request.setState(rs.getBoolean("state"));
        Date fecha = rs.getDate("aprovedDate");
        request.setAprovedDate(fecha != null ? fecha.toLocalDate() : null);
        fecha = rs.getDate("rejectedDate");
        request.setAprovedDate(fecha != null ? fecha.toLocalDate() : null);
        fecha = rs.getDate("finishDate");
        request.setAprovedDate(fecha != null ? fecha.toLocalDate() : null);
        request.setDni_elderly(rs.getString("dni_elderly"));
        request.setIdNumber_contract(rs.getString("idNumber_contract"));
        return request;
    }
}
