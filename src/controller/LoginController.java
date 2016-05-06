package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import DAO.ConnectionFactory;
import DAO.JdbcUsuarioDao;
import model.Usuario;

@Controller
public class LoginController {
	private Connection con = new ConnectionFactory().getConnection();

	@RequestMapping("loginForm")
	public String loginForm() {
		return "formulario-login";
	}

	@RequestMapping("efetuaLogin")
	public String efetuaLogin(Usuario usuario, HttpSession session) throws SQLException {
		if (new JdbcUsuarioDao(con).existeUsuario(usuario)) {
			session.setAttribute("usuarioLogado", usuario);
			return "menu";
		}
		return "redirect:loginForm";
	}

}
