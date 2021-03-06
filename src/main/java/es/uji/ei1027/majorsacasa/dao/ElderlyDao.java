package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Availability;
import es.uji.ei1027.majorsacasa.model.Elderly;
import es.uji.ei1027.majorsacasa.model.SocialWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Repository
public class ElderlyDao {

    private JdbcTemplate jdbcTemplate;
    private SocialWorkerDao socialWorkerDao;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setSocialWorkerDao(SocialWorkerDao socialWorkerDao) {
        this.socialWorkerDao = socialWorkerDao;
    }

    /* Afegeix al Elderly a la base de dades  VIEJO */
    public void addElderlyAdmin(Elderly elderly) {
        jdbcTemplate.update("INSERT INTO Elderly VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", elderly.getDni(),
                elderly.getName(), elderly.getSurname(), elderly.getAddress(), elderly.getBankAccountNumber(), elderly.getUserpwd(), elderly.getEmail(), elderly.getPhoneNumber(), elderly.getBirthDate(), elderly.getDateCreation(), elderly.getAlergies(), elderly.getDiseases(), elderly.getUserCAS_socialWorker());
    }

    /* Afegeix al Elderly a la base de dades */
    public void addElderlyRegistro(Elderly elderly) {
        LocalDate today = LocalDate.now();
        Random rand = new Random();
        SocialWorker socialworker = socialWorkerDao.getSocialWorkers().get(rand.nextInt(socialWorkerDao.getSocialWorkers().size()));

                jdbcTemplate.update("INSERT INTO Elderly VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", elderly.getDni(),
                        elderly.getName(), elderly.getSurname(), elderly.getAddress(), elderly.getBankAccountNumber(), elderly.getUserpwd(), elderly.getEmail(), elderly.getPhoneNumber(), elderly.getBirthDate(), today, elderly.getAlergies(), elderly.getDiseases(), socialworker.getUserCAS());
    }

    /* Esborra al Elderly de la base de dades */
    public void deleteElderly(String dni) {
        jdbcTemplate.update("DELETE from Elderly where dni=?", dni);
    }

    public void deleteElderly(Elderly elderly) {
        jdbcTemplate.update("DELETE from Elderly where dni=?", elderly.getDni());
    }

    /* Actualitza els atributs del Elderly
   (excepte la clau primària)  ADMIN*/
    public void updateElderly(Elderly elderly) {
        jdbcTemplate.update("UPDATE Elderly SET name=?, surname=?, address=?, bankAccountNumber=?, userpwd=?, email=?, phoneNumber=?, birthDate=?, dateCreation=?, alergies=?, diseases=?, userCAS_socialWorker=? WHERE dni=?",
                elderly.getName(), elderly.getSurname(), elderly.getAddress(), elderly.getBankAccountNumber(), elderly.getUserpwd(), elderly.getEmail(), elderly.getPhoneNumber(), elderly.getBirthDate(), elderly.getDateCreation(), elderly.getAlergies(), elderly.getDiseases(), elderly.getUserCAS_socialWorker(), elderly.getDni());
    }

    /* Actualitza els atributs del Elderly
    (excepte la clau primària) */
    public void updateParaElderly(Elderly elderly) {
        jdbcTemplate.update("UPDATE Elderly SET name=?, surname=?, address=?, bankAccountNumber=?, userpwd=?, email=?, phoneNumber=?, birthDate=?, alergies=?, diseases=? WHERE dni=?",
                elderly.getName(), elderly.getSurname(), elderly.getAddress(), elderly.getBankAccountNumber(), elderly.getUserpwd(), elderly.getEmail(), elderly.getPhoneNumber(), elderly.getBirthDate(), elderly.getAlergies(), elderly.getDiseases(), elderly.getDni());
    }


    /* Obté el Elderly amb el nom donat. Torna null si no existeix. */
    public Elderly getElderly(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Elderly WHERE dni=?",
                    new ElderlyRowMapper(),
                    dni);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els elderlys. Torna una llista buida si no n'hi ha cap. */
    public List<Elderly> getElderlys() {
        try {
            return jdbcTemplate.query("SELECT * FROM Elderly ORDER BY dni",
                    new ElderlyRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Elderly>();
        }
    }
/*
    public List<Availability> getAvaiabilities(String dni) {
        try {
            return jdbcTemplate.query("SELECT * FROM Availability WHERE dni_volunteer=?",
                    new AvailabilityRowMapper(), dni);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Availability>();
        }
    }*/
}