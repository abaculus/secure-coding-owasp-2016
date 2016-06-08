package se.panok.securecoding.a4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http/*.csrf().disable()*/.authorizeRequests()
        .antMatchers("/hello", "/search", "/adduser").permitAll()
        .antMatchers("/secret").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
        .anyRequest().fullyAuthenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/secret")
        .usernameParameter("username")
        .passwordParameter("password")
        .permitAll()
        .and()
        .logout()
        .logoutSuccessUrl("/login")
        .permitAll()
        .and()
        .rememberMe()
        .and()
        .sessionManagement()
        .maximumSessions(2)
        .maxSessionsPreventsLogin(true);
		
		// http://docs.spring.io/autorepo/docs/spring-security/current/apidocs/org/springframework/security/config/annotation/web/builders/HttpSecurity.html
		// http://docs.spring.io/autorepo/docs/spring-security/current/apidocs/org/springframework/security/config/annotation/web/configurers/HeadersConfigurer.html
		/*
		 * The default headers include are:
		 * 
		 * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
		 * Pragma: no-cache
		 * Expires: 0
		 * X-Content-Type-Options: nosniff
		 * Strict-Transport-Security: max-age=31536000 ; includeSubDomains
		 * X-Frame-Options: DENY
		 * X-XSS-Protection: 1; mode=block
		 */
		http
        .headers()
        	// Content-Security-Policy
        	.addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"))
            .contentTypeOptions().and()
            .xssProtection().and()
            .cacheControl().and()
            .httpStrictTransportSecurity().and()
            .frameOptions();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password("$2a$10$qiv3S.wTdSzBRGeLXSudFOgcJqczOXSueNydbMBkSOEHPS6KUAY.2").roles("USER");
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("admin").password("$2a$10$q4fZwAdJrCwtBVgzah/9o.Erze7zPENwubPZH58TXIuUJQbpFDjv6").roles("ADMIN");
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("andreas").password("$2a$10$p2jRw6jBTZ7F7sSy6y25tuHtnK.KgA.uXJ4pNoeAvM16Y5sV3slNm").roles("ADMIN");
	}
}