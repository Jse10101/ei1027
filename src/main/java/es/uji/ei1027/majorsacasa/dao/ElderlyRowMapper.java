package es.uji.ei1027.majorsacasa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.majorsacasa.model.Elderly;

public class ElderlyRowMapper implements RowMapper<Elderly> {
	
	@Override
	public Elderly mapRow(ResultSet rs, int rowNum) throws SQLException {
		Elderly elderly = new Elderly();
		elderly.setDni(rs.getString("dni"));
		elderly.setSurname(rs.getString("surname"));
		elderly.setAddress(rs.getString("address"));
		elderly.setBankAccountNumber(rs.getString("bankAccountNumber"));
		elderly.setUserpwd(rs.getString("userpwd"));
		elderly.setEmail(rs.getString("email"));
		elderly.setPhoneNumber(rs.getInt("phoneNumber"));
		elderly.setBirthDate(rs.getDate("birthDate").toLocalDate());
		elderly.setDateCreation(rs.getDate("dateCreation").toLocalDate());
		elderly.setAlergies(rs.getString("alergies"));
		elderly.setDiseases(rs.getString("diseases"));
		elderly.setUserCAS_socialWorker(rs.getString("userCAS_socialWorker"));
		return elderly;
	}
}