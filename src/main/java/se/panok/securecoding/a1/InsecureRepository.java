package se.panok.securecoding.a1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class InsecureRepository {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private JdbcTemplate jdbcTemplate;

	public List<User> getUserByName(final String name) {
		logger.info("\r\n\r\n\t---==# Runnning 'getUserByName(...);' with name = " + name + " #==---");
		return jdbcTemplate.query("SELECT * FROM users WHERE name = '" + name + "'", new RowMapper<User>() {

			@Override
			public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
				return new User(rs.getLong(1), rs.getString(2), rs.getString(3));
			}

		});
	}

	@Autowired
	public void setDataSource(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public static class User {

		private final Long id;

		private final String name;

		private final String password;

		private User(final Long id, final String name, final String password) {
			this.id = id;
			this.name = name;
			this.password = password;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getPassword() {
			return password;
		}

		@Override
		public String toString() {
			return "id=" + id + ", name='" + name + "', password='" + password + "'";
		}
	}
}
