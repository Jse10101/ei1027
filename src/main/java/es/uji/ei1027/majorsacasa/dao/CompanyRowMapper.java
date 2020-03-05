package es.uji.ei1027.majorsacasa.dao;

import es.uji.ei1027.majorsacasa.model.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<Company> {

@Override
public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setName(rs.getString("name"));
        company.setCIF(rs.getString("cif"));
        company.setPwd(rs.getString("pwd"));
        company.setAddress(rs.getString("address"));
        company.setContactName(rs.getString("contactName"));
        company.setContactPhoneNumber(rs.getInt("contactPhoneNumber"));
        company.setContactEmail(rs.getString("contactEmail"));
        company.setServiceType(rs.getString("serviceType"));
        return company;
        }
}
