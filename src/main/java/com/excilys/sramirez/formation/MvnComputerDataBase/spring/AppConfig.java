package com.excilys.sramirez.formation.MvnComputerDataBase.spring;


import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariDataSource;




@Configuration
@PropertySource(value = "classpath:connect.properties")
@ComponentScan("com.excilys.sramirez.formation.MvnComputerDataBase.DAO")
public class AppConfig {

	private final Environment env;

	public AppConfig(Environment env) {
		this.env = env;
	}

	/*@Bean
	public DataSource getDataSource() {
		ResourceBundle bundle = ResourceBundle.getBundle("connect");
		String url = bundle.getString("url");
		String username = bundle.getString("login");
		String password = bundle.getString("password");
		String driver = bundle.getString("driverClassName");

		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setDriverClassName(driver);
		return ds;
	}*/

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("url"));
		dataSource.setUsername(env.getRequiredProperty("login"));
		dataSource.setPassword(env.getRequiredProperty("password"));
		return dataSource;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource datasource) {
		return new JdbcTemplate(datasource);
	}

}