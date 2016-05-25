package se.panok.securecoding.a3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import se.panok.securecoding.a1.SecureRepository;
import se.panok.securecoding.a1.SecureRepository.User;

@Controller
public class HelloController {

	@Autowired
	private SecureRepository secureRepository;

	@RequestMapping("/badhello")
	public String renderBadHello(final ModelMap model) {

		populateModel(model);

		return "badhello";
	}
	
	@RequestMapping("/goodhello")
	public String renderGoodHello(final ModelMap model) {

		populateModel(model);

		return "goodhello";
	}
	
	private void populateModel(final ModelMap model) {
		final User user = secureRepository.getUserByName("js").get(0);
		model.addAttribute("name", user.getName());
		model.addAttribute("password", user.getPassword());
	}
}
