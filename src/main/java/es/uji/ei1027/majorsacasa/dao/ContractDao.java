package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ContractDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    /* insert new contract into the data */
    public void addContract(Contract contract) {
        jdbcTemplate.update("INSERT INTO Contract VALUES(?, ?, ?, ?, ?, ?)",
        		contract.getIdNumber(), contract.getDateBegining(), contract.getDateEnding(), contract.getServiceType(), contract.getPrice(), contract.getCif_company());
    }

    /* delete contract from db */
    public void deleteContract(int idNumber) {
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
    public Contract getContract(int idNumber) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE idNumber=?",
      		        new ContractRowMapper(),
      		      idNumber);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els elderlys. Torna una llista buida si no n'hi ha cap. */
    public List<Contract> getContracts() {
        try {
            return jdbcTemplate.query("SELECT * FROM Contract",
           	     new ContractRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Contract>();
        }
    }
   }
