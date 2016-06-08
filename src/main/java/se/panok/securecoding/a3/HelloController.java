package se.panok.securecoding.a3;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import se.panok.securecoding.a1.SecureRepository;
import se.panok.securecoding.a1.SecureRepository.User;

@Controller
public class HelloController {

	@Autowired
	private SecureRepository secureRepository;

	@RequestMapping("/hello")
	public String renderBadHello(final ModelMap model, @RequestParam("username") final String username,
			@RequestParam(value = "good", required = false) final Optional<Boolean> good) {

		populateModel(model, username);

		return good.isPresent() && good.get() ? "goodhello" : "badhello";
	}

	private void populateModel(final ModelMap model, final String username) {
		final User user = secureRepository.getUserByName(username).get(0);
		model.addAttribute("name", user.getName());
		model.addAttribute("password", user.getPassword());
	}
}
