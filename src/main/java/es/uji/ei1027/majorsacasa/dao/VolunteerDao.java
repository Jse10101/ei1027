package es.uji.ei1027.majorsacasa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.majorsacasa.model.Volunteer;


@Repository
public class VolunteerDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix al Volunteer a la base de dades */
    public void addVolunteer(Volunteer volunteer) {
        jdbcTemplate.update("INSERT INTO Volunteer VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", volunteer.getName(), volunteer.getDni(), volunteer.getPhoneNumber(), volunteer.getEmail(),
                volunteer.getPwd(), volunteer.getHobbies(), volunteer.getApplicationDate(), volunteer.getAcceptationDate(), volunteer.getFinishDate(), volunteer.getAccepted(), volunteer.getBirthDate());
    }

    /* Esborra al Elderly de la base de dades */
    public void deleteVolunteer(String dni) {
        jdbcTemplate.update("DELETE from Volunteer where dni=?", dni);
    }

    public void delteVolunteer(Volunteer volunteer) {
        jdbcTemplate.update("DELETE from Volunteer where dni=?", volunteer.getDni());
    }

    /* Actualitza els atributs del Elderly
   (excepte la clau primària) */
    public void updateVolunteer(Volunteer volunteer) {
        jdbcTemplate.update("UPDATE Volunteer SET name=?, phoneNumber=?, email=?, pwd=?, hobbies=?, applicationDate=?, acceptationDate=?, finishDate=?, accepted=?, birthbDate=? WHERE dni=?",
                volunteer.getName(), volunteer.getPhoneNumber(), volunteer.getEmail(), volunteer.getPwd(), volunteer.getHobbies(),
                volunteer.getApplicationDate(), volunteer.getAcceptationDate(), volunteer.getFinishDate(), volunteer.getAccepted(), volunteer.getBirthDate(), volunteer.getDni());
    }

    /* Obté el Elderly amb el nom donat. Torna null si no existeix. */
    public Volunteer getVolunteer(String user) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE user=?",
                    new VolunteerRowMapper(),
                    user);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els elderlys. Torna una llista buida si no n'hi ha cap. */
    public List<Volunteer> getVolunteers() {
        try {
            return jdbcTemplate.query("SELECT * FROM Volunteer",
                    new VolunteerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Volunteer>();
        }
    }
}