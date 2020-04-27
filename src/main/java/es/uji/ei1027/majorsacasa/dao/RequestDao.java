package es.uji.ei1027.majorsacasa.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.majorsacasa.model.Request;


@Repository
public class RequestDao {

    private JdbcTemplate jdbcTemplate;
    private int id=1;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    /* Afegeix la Request a la base de dades */
    public void addRequest(Request request) {
        jdbcTemplate.update("INSERT INTO Request VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", request.getIdNumber(),
                request.getServiceType(), request.getCreationDate(), request.getState(), request.getAprovedDate(), request.getRejectedDate(), request.getFinishDate(), request.getDni_elderly(), request.getIdNumber_contract());
    }

    /* Esborra la Request de la base de dades */
    public void deleteRequest(String idNumber) {
        jdbcTemplate.update("DELETE from Request where idNumber=?", idNumber);
    }

    public void deleteRequest(Request request) {
        jdbcTemplate.update("DELETE from Request where idNumber=?", request.getIdNumber());
    }

    /* Actualitza els atributs de la Request
   (excepte la clau primària) */
    public void updateRequest(Request request) {
        jdbcTemplate.update("UPDATE Request SET serviceType=?, creationDate=?, state=?, aprovedDate=?, rejectedDate=?, finishDate=?, dni_elderly=?, idNumber_contract=? WHERE idNumber=?",
                request.getServiceType(), request.getCreationDate(), request.getState(), request.getAprovedDate(), request.getRejectedDate(), request.getFinishDate(), request.getDni_elderly(), request.getIdNumber_contract(), request.getIdNumber());
    }
    
    // Obté la Request amb el id donat. Torna null si no existeix. 
    public Request getRequest(String idNumber) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE idNumber=?",
                    new RequestRowMapper(),
                    idNumber);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
        

    /* Obté totes les request. Torna una llista buida si no n'hi ha cap. */
    public List<Request> getRequests() {
        try {
            return jdbcTemplate.query("SELECT * FROM Request ORDER BY idNumber",
                    new RequestRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
    
    
    
    //NOVA REQUEST BAIXA
    
    /* Actualitza els atributs de la Request
   (excepte la clau primària) */
    public void donaDeBaixaRequest(String idNumber) {
    	LocalDate today = LocalDate.now();
        jdbcTemplate.update("UPDATE Request SET state=?, finishDate=? WHERE idNumber=?",
                false, today, idNumber);
    }
    
    
    
    //NOVA REQUEST ADD
    
    /* Actualitza els atributs de la Request
   (excepte la clau primària) */
    public void donaDeAltaRequest(Request request) {
    	LocalDate today = LocalDate.now();
        jdbcTemplate.update("INSERT INTO Request VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", Integer.toString(id++),
                request.getServiceType(), today, false, null, null, null, request.getDni_elderly(), null);
    }
}
