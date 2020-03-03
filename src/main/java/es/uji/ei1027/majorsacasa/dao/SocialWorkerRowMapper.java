package es.uji.ei1027.majorsacasa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.majorsacasa.model.SocialWorker;

public final class SocialWorkerRowMapper implements RowMapper<SocialWorker>{

	@Override
	public SocialWorker mapRow(ResultSet rs, int rowNum) throws SQLException {
		SocialWorker socialWorker = new SocialWorker();
		socialWorker.setName(rs.getString("socialWorker_name"));
		socialWorker.setUserCAS(rs.getString("socialWorker_userCAS"));
		socialWorker.setPwd(rs.getString("socialWorker_pwd"));
		socialWorker.setPhoneNumber(rs.getInt("socialWorker_phoneNumber"));
		socialWorker.setEmail(rs.getString("socialWorker_email"));
		return socialWorker;
	}

}