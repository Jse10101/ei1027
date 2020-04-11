package es.uji.ei1027.majorsacasa.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.majorsacasa.model.Login;

@Repository
public class LoginDao {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public void addLogin(Login login) {
		jdbcTemplate.update("INSERT INTO Login VALUES(?, ?, ?)", login.getUser(), login.getPassword(),
				login.getRole());
		
	}
	
	public Login getLogin(String user, String password) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from Login WHERE user=? AND password=?",
					new LoginRowMapper(), user, password);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
