package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Availability;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AvailabilityRowMapper implements RowMapper<Availability> {

    @Override
    public Availability mapRow(ResultSet rs, int rowNum) throws SQLException {
        Availability availability = new Availability();
        availability.setFecha(rs.getDate("fecha").toLocalDate());
        availability.setBeginingHour(rs.getTime("beginingHour").toLocalTime());
        availability.setEndingHour(rs.getTime("endingHour").toLocalTime());
        availability.setStateAvailability(rs.getBoolean("stateAvailability"));
        availability.setDni_volunteer(rs.getString("dni_volunteer"));

        String dni = rs.getString("dni_elderly");
        availability.setDni_elderly(dni != null ? dni : null);
        return availability;
    }
}
