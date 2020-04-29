package es.uji.ei1027.majorsacasa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.majorsacasa.model.Elderly;
import es.uji.ei1027.majorsacasa.model.Login;

@Repository
public class LoginDao {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public void addLogin(Login login) {

		jdbcTemplate.update("INSERT INTO Login VALUES(?, ?, ?)", login.getUsuario(), login.getPwd(),
				login.getRole());
		
	}
	
	public Login getLogin(String usuario, String pwd) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from Login WHERE usuario=? AND pwd=?",
					new LoginRowMapper(), usuario, pwd);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	 /* Actualitza els atributs del Elderly
    (excepte la clau primària) */
	public void updateLogin(Login login) {
		jdbcTemplate.update("UPDATE Login SET pwd=? WHERE usuario=?",
				login.getPwd());
 }
	
	 /* Obté el Elderly amb el nom donat. Torna null si no existeix. */
	 public Elderly getLogin(String usuario) {
	     try {
	         return jdbcTemplate.queryForObject("SELECT * FROM Login WHERE usuario=?",
	   		        new ElderlyRowMapper(),
	    				usuario);
	     }
	     catch(EmptyResultDataAccessException e) {
	         return null;
	     }
	 }
	 
		/* Esborra al Elderly de la base de dades */
		public void deleteLogin(String username) {
			jdbcTemplate.update("DELETE from Login where usuario=?", username);
		}
	 
	 
	 /* Obté tots els elderlys. Torna una llista buida si no n'hi ha cap. */
	 public List<Login> getLogins() {
	     try {
	         return jdbcTemplate.query("SELECT * FROM Login ORDER BY usuario",
	        	     new LoginRowMapper());
	     }
	     catch(EmptyResultDataAccessException e) {
	         return new ArrayList<Login>();
	     }
	 }
	
}
