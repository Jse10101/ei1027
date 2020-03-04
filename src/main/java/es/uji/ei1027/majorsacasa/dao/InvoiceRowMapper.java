package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Elderly;
import es.uji.ei1027.majorsacasa.model.Invoice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceRowMapper implements RowMapper<Invoice> {


    @Override
    public Invoice mapRow(ResultSet resultSet, int i) throws SQLException {
        Invoice invoice = new Invoice();

        invoice.setConcept(resultSet.getString("concept"));
        invoice.setAmount(resultSet.getInt("amount"));
        invoice.setElderlyDNI(resultSet.getInt("elderlyDNI"));
        invoice.setFecha(resultSet.getDate("fecha").toLocalDate());
        invoice.setIdNumber(resultSet.getInt("idNumber"));
        return invoice;
    }
}
