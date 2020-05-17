package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Company;
import es.uji.ei1027.majorsacasa.model.Contract;
import es.uji.ei1027.majorsacasa.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractDao {


    private JdbcTemplate jdbcTemplate;
    private CompanyDao companyDao;
    private int id = 1;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    /* insert new contract into the data */
    public void addContract(Contract contract) {
        jdbcTemplate.update("INSERT INTO Contract VALUES(?, ?, ?, ?, ?, ?)",
                contract.getIdNumber(), contract.getDateBegining(), contract.getDateEnding(), contract.getServiceType(), contract.getPrice(), contract.getCif_company());
    }

    /* delete contract from db */
    public void deleteContract(String idNumber) {
        jdbcTemplate.update("DELETE from Contract where idNumber=?", idNumber);
    }

    public void deleteContract(Contract contract) {
        jdbcTemplate.update("DELETE from Contract where idNumber=?", contract.getIdNumber());
    }

    /* update invoice contract excep primary key*/
    public void updateContract(Contract contract) {
        jdbcTemplate.update("UPDATE Contract SET idNumber=?, dateBegining=?, dateEnding=?, serviceType=?, price=?, cif_company=?",
                contract.getIdNumber(), contract.getDateBegining(), contract.getDateEnding(), contract.getServiceType(), contract.getPrice(), contract.getCif_company());
    }


    /* Obté el Elderly amb el nom donat. Torna null si no existeix. */
    public Contract getContract(String idNumber) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE idNumber=?",
                    new ContractRowMapper(),
                    idNumber);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els elderlys. Torna una llista buida si no n'hi ha cap. */
    public List<Contract> getContracts() {
        try {
            return jdbcTemplate.query("SELECT * FROM Contract ORDER BY idNumber",
                    new ContractRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Contract>();
        }
    }

    /* Actualitza els atributs dels Contrats
   (excepte la clau primària) */
    /*public void nouContract(Request request) {
        LocalDate today = LocalDate.now();

        //Creamos un requestDao, hacemos una lista de Requests y la recorremos para comprobar el valor de id
        List<Contract> listaContracts = this.getContracts();
        for (Contract con : listaContracts) {
            if (Integer.valueOf(con.getIdNumber()) > id) {
                id = Integer.valueOf(con.getIdNumber());
            }
        }
        id++;
        List<Company> listaCompaniesServici = null;
        for (Company com : companyDao.getCompanies()){
            if(request.getServiceType().equals(com.getServiceType()))
                listaCompaniesServici.add(com);
        }
        Company company = listaCompaniesServici.get((int)Math.random()*(listaCompaniesServici.size()-0+1)+0);
        switch (request.getServiceType()){

        }
            jdbcTemplate.update("INSERT INTO Contract VALUES(?, ?, ?, ?, ?, ?, ?)", Integer.toString(id),
                    request.getServiceType(), true, request.getAprovedDate(), request.getAprovedDate().plusDays(7), company.getPrice(), company.getCif());
    }*/
}
