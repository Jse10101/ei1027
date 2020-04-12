package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class VolunteerRowMapper implements RowMapper<Volunteer> {

    public Volunteer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Volunteer volunteer = new Volunteer();
        volunteer.setName(rs.getString("name"));
        volunteer.setDni(rs.getString("dni"));
        volunteer.setPwd(rs.getString("pwd"));
        volunteer.setEmail(rs.getString("email"));
        volunteer.setPhoneNumber(rs.getString("phoneNumber"));
        volunteer.setHobbies(rs.getString("hobbies"));
        volunteer.setApplicationDate(rs.getDate("applicationDate").toLocalDate());
        Date fecha = rs.getDate("acceptationDate");
        volunteer.setAcceptationDate(fecha != null ? fecha.toLocalDate() : null);
        fecha = rs.getDate("finishDate");
        volunteer.setFinishDate(fecha != null ? fecha.toLocalDate() : null);
        volunteer.setAccepted(rs.getBoolean("accepted"));
        volunteer.setBirthDate(rs.getDate("birthDate").toLocalDate());
        return volunteer;
    }
}
