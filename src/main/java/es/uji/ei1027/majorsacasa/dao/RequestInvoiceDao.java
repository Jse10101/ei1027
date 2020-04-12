package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.RequestInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestInvoiceDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    /* Afegeix RequestInvoice a la base de dades */
    public void addRequestInvoice(RequestInvoice requestInvoice) {
        jdbcTemplate.update("INSERT INTO isPayedBy VALUES(?, ?)", requestInvoice.getIdNumber_request(), requestInvoice.getIdNumber_invoice());
    }

    /* Esborra RequestInvoice de la base de dades */
    public void deleteRequestInvoice(String id_invoice, String id_request) {
        jdbcTemplate.update("DELETE from isPayedBy where idNumber_invoice=? and idNumber_request=?", id_invoice, id_request);
    }

    public void deleteRequestInvoice(RequestInvoice requestInvoice) {
        jdbcTemplate.update("DELETE from isPayedBy where idNumber_invoice=? and idNumber_request=?", requestInvoice.getIdNumber_invoice(), requestInvoice.getIdNumber_request());
    }

    /* Actualitza els atributs de RequestInvoice
   (excepte les claus primàries) */

    /* Obté el RequestInvoice amb els ids donats. Torna null si no existeix. */
    public RequestInvoice getRequestInvoice(String id_invoice, String id_request) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM isPayedBy WHERE idNumber_invoice=? and idNumber_request=?",
                    new RequestInvoiceRowMapper(),
                    id_invoice, id_request);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els requestinvoice. Torna una llista buida si no n'hi ha cap. */
    public List<RequestInvoice> getRequestInvoices() {
        try {
            return jdbcTemplate.query("SELECT * FROM isPayedBy",
                    new RequestInvoiceRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<RequestInvoice>();
        }
    }
}
