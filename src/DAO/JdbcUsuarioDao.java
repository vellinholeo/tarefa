package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import model.Usuario;

public class JdbcUsuarioDao {
	private Connection connection;

	public JdbcUsuarioDao(Connection connection) throws SQLException {
		this.connection = connection;
	}

	public boolean existeUsuario(Usuario usuario) {
		Usuario u = new Usuario();
		PreparedStatement stmt;
		ResultSet rs;
		try {  
			
			stmt = this.connection.prepareStatement("select * from usuarios where login=? and senha=?");
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			rs = stmt.executeQuery();
			if (rs.next()) {
				u.setLogin(rs.getString("login"));
				u.setSenha(rs.getString("senha"));
				return true;
			} else {
				return false;
			}
			// u.setDataFinalizacao(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
