package es.uji.ei1027.majorsacasa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.majorsacasa.model.Request;

public class RequestRowMapper implements RowMapper<Request> {

    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();
        request.setIdNumber(rs.getInt("idNumber"));
        request.setServiceType(rs.getString("serviceType"));
        request.setCreationDate(rs.getDate("creationDate"));
        request.setState(rs.getBoolean("state"));
        request.setAprovedDate(rs.getDate("aprovedDate"));
        request.setRejectedDate(rs.getDate("rejectedDate"));
        request.setFinishDate(rs.getDate("finishDate"));
        request.setDni_elderly(rs.getString("dni_elderly"));
        request.setIdNumber_contract(rs.getInt("idNumber_contract"));
        return request;
    }
}
