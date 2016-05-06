package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import DAO.ConnectionFactory;
import DAO.JdbcTarefaDao;
import model.Tarefa;

@Controller
public class TarefasController {
	private Connection con = new ConnectionFactory().getConnection();

	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";
	}

	@RequestMapping("adicionaTarefa")
	public String adiciona(@Valid Tarefa tarefa, BindingResult result) throws SQLException {
		if (result.hasFieldErrors("descricao")) {
			return "tarefa/formulario";
		}
		JdbcTarefaDao dao = new JdbcTarefaDao(con);
		dao.adiciona(tarefa);

		return "redirect:listaTarefas";
	}
	
	@RequestMapping("listaTarefas")
	public String lista(Model model) throws SQLException {
	  JdbcTarefaDao dao = new JdbcTarefaDao(con);
	  List<Tarefa> tarefas = dao.lista(); 
	  
	  model.addAttribute("tarefas", tarefas);
	  return "tarefa/lista";
	}
	
	@RequestMapping("removeTarefa")
	public String remove(Tarefa tarefa) throws SQLException {
	  JdbcTarefaDao dao = new JdbcTarefaDao(con);
	  dao.remove(tarefa);
	  return "redirect:listaTarefas";
	}
	
	@RequestMapping("mostraTarefa")
	public String mostra(Long id, Model model) throws SQLException {
	  JdbcTarefaDao dao = new JdbcTarefaDao(con);
	  model.addAttribute("tarefa", dao.buscaPorId(id));
	  return "tarefa/mostra";
	}
	
	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa) throws SQLException{
	  JdbcTarefaDao dao = new JdbcTarefaDao(con);
	  dao.altera(tarefa);
	  return "redirect:listaTarefas";
	}
	
	@RequestMapping("finalizaTarefa")
	public String finaliza(Long id, Model model) throws SQLException {
	  JdbcTarefaDao dao = new JdbcTarefaDao(con);
	  dao.finaliza(id);
	  model.addAttribute("tarefa", dao.buscaPorId(id));
	  return "tarefa/finalizada";
	}

}
