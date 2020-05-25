package es.uji.ei1027.majorsacasa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.majorsacasa.model.SocialWorker;

public final class SocialWorkerRowMapper implements RowMapper<SocialWorker>{

	@Override
	public SocialWorker mapRow(ResultSet rs, int rowNum) throws SQLException {
		SocialWorker socialWorker = new SocialWorker();
		socialWorker.setName(rs.getString("name"));
		socialWorker.setUserCAS(rs.getString("userCAS"));
		socialWorker.setPwd(rs.getString("pwd"));
		socialWorker.setPhoneNumber(rs.getString("phoneNumber"));
		socialWorker.setEmail(rs.getString("email"));
		return socialWorker;
	}

}