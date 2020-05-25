package es.uji.ei1027.majorsacasa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.majorsacasa.model.Login;



public final class LoginRowMapper implements RowMapper<Login> {
    public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
        Login login = new Login();
        login.setUsuario(rs.getString("usuario"));
        login.setPwd(rs.getString("pwd"));
        login.setRole(rs.getString("role"));
        return login;
    }
}
