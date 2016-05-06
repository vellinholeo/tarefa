package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Tarefa;

public class JdbcTarefaDao {
	private Connection connection;

	public JdbcTarefaDao(Connection connection) throws SQLException {
		this.connection = connection;
	}

	public void adiciona(Tarefa tarefa) {
		String sql = "insert into tarefas " + "(descricao)" + " values (?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);
			// seta os valores
			stmt.setString(1, tarefa.getDescricao());
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Tarefa> lista() throws SQLException {
		List<Tarefa> listTarefa = new ArrayList<Tarefa>();
		PreparedStatement stmt = this.connection.prepareStatement("select * from tarefas");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			// criando o objeto Contato
			Tarefa tarefa = new Tarefa();
			tarefa.setId(rs.getLong("id"));
			tarefa.setDescricao(rs.getString("descricao"));
			tarefa.setFinalizado(rs.getBoolean("finalizado"));

			// montando a data através do Calendar
			if (rs.getDate("dataFinalizacao") != null) {
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("dataFinalizacao"));
				tarefa.setDataFinalizacao(data);
			}
			// adicionando o objeto à lista
			listTarefa.add(tarefa);
		}

		rs.close();
		stmt.close();

		return listTarefa;
	}

	public void remove(Tarefa tarefa) {
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from tarefas where id=?");
			stmt.setLong(1, tarefa.getId());
			stmt.execute();
			stmt.close();
			System.out.println("Removido");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Tarefa buscaPorId(Long id) {
		Tarefa t = new Tarefa();
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = this.connection.prepareStatement("select * from tarefas where id=?");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			rs.next();
			t.setId(id);
			t.setDescricao(rs.getString("descricao"));
			t.setFinalizado(rs.getBoolean("finalizado"));

			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("dataFinalizacao"));
			t.setDataFinalizacao(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public void altera(Tarefa t) {
		String sql = "update tarefas set descricao=?, finalizado=?," + "dataFinalizacao=? where id=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, t.getDescricao());
			stmt.setBoolean(2, t.isFinalizado());
			stmt.setDate(3, new Date(t.getDataFinalizacao().getTimeInMillis()));
			stmt.setLong(4, t.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void finaliza(long id) {
		String sql = "update tarefas set finalizado=?, dataFinalizacao=? where id=?";
		try {
			Calendar c = Calendar.getInstance();
			PreparedStatement stmt = connection.prepareStatement(sql);			
			stmt.setBoolean(1, true);
			stmt.setLong(3, id);
			stmt.setDate(2, new Date(c.getTimeInMillis()));			
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
