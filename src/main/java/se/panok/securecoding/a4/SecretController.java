package se.panok.securecoding.a4;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecretController {

	//@PreAuthorize("hasAuthority('USER')")
	@RequestMapping("/secret")
	public String renderSecret() {
		return "secret";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping("/supersecret")
	public String renderSuperSecret() {
		return "supersecret";
	}
}
