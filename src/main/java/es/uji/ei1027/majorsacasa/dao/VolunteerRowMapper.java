package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Elderly;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VolunteerRowMapper implements RowMapper<Volunteer> {

    @Override
    public Volunteer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Volunteer volunteer = new Volunteer();
        volunteer.setName(rs.getString("name"));
        volunteer.setDni(rs.getString("dni"));
        volunteer.setPwd(rs.getString("pwd"));
        volunteer.setEmail(rs.getString("email"));
        volunteer.setPhoneNumber(rs.getInt("phoneNumber"));
        volunteer.setHobbies(rs.getString("hobbies"));
        volunteer.setApplicationDate(rs.getDate("applicationDate").toLocalDate());
        volunteer.setAcceptationDate(rs.getDate("acceptationDate").toLocalDate());
        volunteer.setFinishDate(rs.getDate("finishDate").toLocalDate());
        volunteer.setAccepted(rs.getBoolean("accepted"));
        volunteer.setBirthDate(rs.getDate("birthDate").toLocalDate());
        return volunteer;
    }
}
