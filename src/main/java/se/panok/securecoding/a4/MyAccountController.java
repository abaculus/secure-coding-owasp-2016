package se.panok.securecoding.a4;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import se.panok.securecoding.a1.SecureRepository;
import se.panok.securecoding.a1.SecureRepository.User;

@Controller
public class MyAccountController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecureRepository secureRepository;

	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	@RequestMapping("/account")
	public String renderMyAcoount(@RequestParam("username") final String username,
			@RequestParam(value = "good", required = false) final Optional<Boolean> good, final ModelMap modelMap) {

		if ((good.isPresent() && good.get()) && !getPrincipal().equalsIgnoreCase(username)) {
			throw new UnauthorizedException();
		}

		populateModel(modelMap, username);
		return "myaccount";
	}

	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void handleUnauthorizedAction() {
		logger.warn("{} did something not allowed to do and we blocked it!", getPrincipal());
	}

	private void populateModel(final ModelMap modelMap, final String username) {
		final User myAccountUser = secureRepository.getUserByName(username).get(0);
		logger.info("user by username = '{}' = {}", username, myAccountUser);
		modelMap.addAttribute("myAccountUser", myAccountUser);
	}

	private String getPrincipal() {
		final Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (obj instanceof UserDetails) {
			return ((UserDetails) obj).getUsername();
		} else {
			return "unknown";
		}
	}
}
