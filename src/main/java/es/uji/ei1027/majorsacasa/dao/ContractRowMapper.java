package es.uji.ei1027.majorsacasa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import es.uji.ei1027.majorsacasa.model.Contract;

public class ContractRowMapper implements RowMapper<Contract>{

	@Override
	public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contract contract = new Contract();
		contract.setIdNumber(rs.getInt("idNumber"));
		contract.setDateBegining(rs.getDate("dateBegining").toLocalDate());
		contract.setDateEnding(rs.getDate("dateEnding").toLocalDate());
		contract.setServiceType(rs.getString("serviceType"));
		contract.setPrice(rs.getInt("price"));
		contract.setCif_company(rs.getString("cif_company"));
		return contract;
	}

}
