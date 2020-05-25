package es.uji.ei1027.majorsacasa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.majorsacasa.model.SocialWorker;

@Repository
public class SocialWorkerDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDateSource(DataSource dataSource) {
		jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	/* Afegeix al SocialWorker a la base de dades */
	public void addSocialWorker(SocialWorker socialWorker) {
		jdbcTemplate.update("INSERT INTO SocialWorker VALUES(?, ?, ?, ?, ?)", socialWorker.getName(),
				socialWorker.getUserCAS(), socialWorker.getPwd(), socialWorker.getPhoneNumber(), socialWorker.getEmail());
	}
	
	/* Esborra al SocialWorker de la base de dades */
	public void deleteSocialWorker(String userCAS) {
		jdbcTemplate.update("DELETE from SocialWorker where userCAS=?", userCAS);
	}
	
	public void deleteSocialWorker(SocialWorker socialWorker) {
		jdbcTemplate.update("DELETE from SocialWorker where userCAS=?", socialWorker.getUserCAS());
	}
	
	/*
	 * Actualitza els atributs del SocialWorker (excepte la clau primària)
	 */
	public void updateSocialWorker(SocialWorker socialWorker) {
		jdbcTemplate.update("UPDATE SocialWorker SET name=?, pwd=?, phoneNumber=? where userCAS=?",
				socialWorker.getName(), socialWorker.getPwd(), socialWorker.getPhoneNumber(),
				socialWorker.getUserCAS());
	}
	
	public SocialWorker getSocialWorker(String userCAS) {
		try {
			return jdbcTemplate.queryForObject("SELECT * from SocialWorker WHERE userCAS=?", new SocialWorkerRowMapper(), userCAS);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	//
//    @RequestMapping(value = "/delete/{cif}")
//    public String processDelete(@PathVariable String CIF) {
//        companyDao.deleteCompany(CIF);
//        return "redirect:../list";
//    }
	public List<SocialWorker> getSocialWorkers() {
		try {
			return jdbcTemplate.query("Select * from SocialWorker ORDER BY userCAS", new SocialWorkerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<SocialWorker>();
		}

	}
}