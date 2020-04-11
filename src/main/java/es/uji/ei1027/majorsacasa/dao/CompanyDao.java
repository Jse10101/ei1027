package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix al Company a la base de dades */
    public void addCompany(Company company) {
        jdbcTemplate.update("INSERT INTO Company VALUES(?, ?, ?, ?, ?, ?, ?, ?)", company.getName(), company.getCif(), company.getPwd(), company.getAddress(),
                company.getContactName(), company.getContactPhoneNumber(), company.getContactEmail(), company.getServiceType());
    }

    /* Esborra el Company de la base de dades */
    public void deleteCompany(String CIF) {
        jdbcTemplate.update("DELETE from Company where cif=?", CIF);
    }

    public void deleteCompany(Company company) {
        jdbcTemplate.update("DELETE from Company where cif=?", company.getCif());
    }

    /* Actualitza els atributs del Company
   (excepte la clau primària) */
    public void updateCompany(Company company) {
        jdbcTemplate.update("UPDATE Company SET name=?, pwd=?, address=?, contactName=?, contactPhoneNumber=?, contactEmail=?, serviceType=? WHERE cif=?",
                company.getName(), company.getPwd(), company.getAddress(), company.getContactName(), company.getContactPhoneNumber(), company.getContactPhoneNumber(),
                company.getContactEmail(), company.getCif());
    }

    /* Obté el Company per el seu CIF. Torna null si no existeix. */
    public Company getCompany(String CIF) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Company WHERE cif=?",
                    new CompanyRowMapper(),
                    CIF);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els companies. Torna una llista buida si no n'hi ha cap. */
    public List<Company> getCompnanies() {
        try {
            return jdbcTemplate.query("SELECT * FROM Company",
                    new CompanyRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Company>();
        }
    }

}
