package se.panok.securecoding.a4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import se.panok.securecoding.a1.SecureRepository;
import se.panok.securecoding.a1.SecureRepository.User;

@Controller
public class MyAccountController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecureRepository secureRepository;

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@RequestMapping("/badmyaccount")
	public String renderBadMyAcoount(@RequestParam("username") final String username, final ModelMap modelMap) {
		populateModel(modelMap, username);
		return "myaccount";
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@RequestMapping("/goodmyaccount")
	public String renderGoodMyAcoount(@RequestParam("username") final String username, final ModelMap modelMap) {
		if (getPrincipal().equalsIgnoreCase(username)) {
			populateModel(modelMap, username);
		}
		return "myaccount";
	}
	
	private void populateModel(final ModelMap modelMap, final String username) {
		final User myAccountUser = secureRepository.getUserByName(username).get(0);
		logger.info("user by username = '{}' = {}", username, myAccountUser);
		modelMap.addAttribute("myAccountUser", myAccountUser);
	}
	
	private String getPrincipal() {
		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		if (obj instanceof UserDetails) {
			return ((UserDetails) obj).getUsername();
		} else {
			return "unknown";
		}
	}
}
