package se.panok.securecoding.a8;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CsrfControllerAdvice {

    @Autowired
    private HttpServletRequest request;

    @ModelAttribute("_csrf")
    public CsrfToken appendCSRFToken(){
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
}
