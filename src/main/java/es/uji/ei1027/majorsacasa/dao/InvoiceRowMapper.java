package es.uji.ei1027.majorsacasa.dao;


import es.uji.ei1027.majorsacasa.model.Invoice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceRowMapper implements RowMapper<Invoice> {


    @Override
    public Invoice mapRow(ResultSet resultSet, int i) throws SQLException {
        Invoice invoice = new Invoice();

        invoice.setFecha(resultSet.getDate("fecha").toLocalDate());
        invoice.setIdNumber(resultSet.getString("idNumber"));
        invoice.setAmount(resultSet.getInt("amount"));
        invoice.setConcept(resultSet.getString("concept"));
        invoice.setElderlyDNI(resultSet.getString("dni_elderly"));

        return invoice;
    }
}
