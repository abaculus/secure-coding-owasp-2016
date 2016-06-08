package se.panok.securecoding.a1;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserAddController {

	@Autowired
	private DataSource dataSource;

	@RequestMapping("/adduser")
	public String renderAddUser(final ModelMap model) {
		return "adduser";
	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String addUser(@RequestParam("username") final String username,
			@RequestParam("password") final String password, final ModelMap model) {

		final Object[] params = new Object[] { username, password };
		final int[] types = new int[] { Types.VARCHAR, Types.VARCHAR };
		final int rows = new JdbcTemplate(dataSource).update("INSERT INTO users(name, password) VALUES (?, ?)", params,
				types);
		model.addAttribute("rowsAffected", rows);
		return "adduser";
	}

}
