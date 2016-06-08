package se.panok.securecoding.a1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.panok.securecoding.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class SqlInjectionTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InsecureRepository insecureRepository;
	
	@Autowired
	private SecureRepository secureRepository;
	
	@Test
	public void testIntentionOfInsecureRepository() {
		insecureRepository.getUserByName("andreas").forEach(user -> logger.info("User = {}", user));
	}

	@Test
	public void testSqlInjectionWithInsecureRepository() {
		insecureRepository.getUserByName("foobar' OR '1'='1").forEach(user -> logger.info("User = {}", user));
	}
	
	@Test
	public void testSqlInjectionWithSecureRepository() {
		secureRepository.getUserByName("foobar' OR '1'='1").forEach(user -> logger.info("User = {}", user));
	}
}
