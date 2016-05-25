package se.panok.securecoding;

import org.apache.velocity.tools.generic.EscapeTool;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class EscapeToolAdvice {

	@ModelAttribute("escapeTool")
	public EscapeTool escapeTool() {
		return new EscapeTool();
	}
}
