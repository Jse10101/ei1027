package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Company;
import es.uji.ei1027.majorsacasa.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyDao {

    private JdbcTemplate jdbcTemplate;
    private int id = 0;
    //private ContractDao contractDao;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //@Autowired
    //public void setContractDao(ContractDao contractDao) {
    //this.contractDao = contractDao;
    //}

    /* Afegeix al Company a la base de dades */
    public void addCompany(Company company, List<Contract> listaContracts) {
        LocalDate today = LocalDate.now();
        jdbcTemplate.update("INSERT INTO Company VALUES(?, ?, ?, ?, ?, ?, ?, ?)", company.getName(), company.getCif(), company.getPwd(), company.getAddress(),
                company.getContactName(), company.getContactPhoneNumber(), company.getContactEmail(), company.getServiceType());

        for (Contract con : listaContracts) {
            if (Integer.valueOf(con.getIdNumber()) > id) {
                id = Integer.valueOf(con.getIdNumber());
            }
        }
        id++;

        int precio = 0;
        String serviceType = company.getServiceType();
        switch (serviceType) {
            case "Neteja":
                precio = 10;
                break;
            case "Menjar a domicili":
                precio = 4;
                break;
            case "Servei sanitari":
                precio = 25;
                break;
        }
        jdbcTemplate.update("INSERT INTO Contract VALUES(?, ?, ?, ?, ?, ?)", Integer.toString(id), today, today.plusYears(1), serviceType, precio, company.getCif());
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
    public List<Company> getCompanies() {
        try {
            return jdbcTemplate.query("SELECT * FROM Company ORDER BY cif",
                    new CompanyRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Company>();
        }
    }

}
