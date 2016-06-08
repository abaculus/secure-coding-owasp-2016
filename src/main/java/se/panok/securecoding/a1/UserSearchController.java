package se.panok.securecoding.a1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserSearchController {

	@Autowired
	private InsecureRepository insecureRepository;

	@Autowired
	private SecureRepository secureRepository;

	@RequestMapping("/search")
	public String renderSearch(final ModelMap model) {
		return "search";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(final ModelMap model, @RequestParam("username") final String username,
			@RequestParam(value = "good", required = false) final Optional<Boolean> good) {
		if (good.isPresent() && good.get()) {
			model.addAttribute("users", secureRepository.getUserByName(username));
		} else {
			model.addAttribute("users", insecureRepository.getUserByName(username));
		}
		return "search";
	}

}
