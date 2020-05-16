package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Availability;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AvailabilityDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix al Availability a la base de dades */
    public void addAvailability(Availability availability, Volunteer volunteer) {
        jdbcTemplate.update("INSERT INTO Availability VALUES(?, ?, ?, ?, ?, ?)", availability.getFecha(), availability.getBeginingHour(), availability.getEndingHour(), true,
                volunteer.getDni(), null);
    }

    /* Esborra el Availability de la base de dades */
    public void deleteAvailability(LocalDate fecha, String dni_volunteer, LocalTime beginingHour) {
        jdbcTemplate.update("DELETE from Availability where dni_volunteer=? and fecha=? and beginingHour=?", dni_volunteer, fecha, beginingHour);
    }

    public void deleteAvailability(Availability availability) {
        jdbcTemplate.update("DELETE from Availability where dni_volunteer=? and fecha=? and beginingHour=?", availability.getDni_volunteer(), availability.getFecha(), availability.getBeginingHour());
    }

    /* Actualitza els atributs del Availability
   (excepte la clau primària) */
    public void updateAvailability(Availability availability, Volunteer volunteer) {
        jdbcTemplate.update("UPDATE Availability SET endingHour=?, stateAvailability=?, dni_elderly=? WHERE dni_volunteer=? AND fecha=? AND beginingHour=?",
                availability.getEndingHour(), availability.getStateAvailability(), availability.getDni_elderly(), availability.getDni_volunteer(), availability.getFecha(),
                availability.getBeginingHour());
    }

    /* Obté el Availability. Torna null si no existeix. */
    public Availability getAvailability(LocalDate fecha, String dni_volunteer, LocalTime beginingHour) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Availability WHERE dni_volunteer=? AND fecha=? AND beginingHour=?",
                    new AvailabilityRowMapper(),
                    dni_volunteer, fecha, beginingHour);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els availabilities. Torna una llista buida si no n'hi ha cap. */
    public List<Availability> getAvailabilities() {
        try {
            return jdbcTemplate.query("SELECT * FROM Availability ORDER BY fecha, dni_volunteer",
                    new AvailabilityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Availability>();
        }
    }
    
    /* Actualitza els atributs del Availability
   (excepte la clau primària) */
    public void donaDeAltaAvailability(Availability availability) {
        jdbcTemplate.update("UPDATE Availability SET endingHour=?, stateAvailability=?, dni_elderly=? WHERE dni_volunteer=? AND fecha=? AND beginingHour=?",
                availability.getEndingHour(), availability.getStateAvailability(), availability.getDni_elderly(), availability.getDni_volunteer(), availability.getFecha(),
                availability.getBeginingHour());
    }

}
