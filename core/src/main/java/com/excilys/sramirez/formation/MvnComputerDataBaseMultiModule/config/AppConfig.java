package com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.config;


import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;
 



@Configuration
@ComponentScan(basePackages= {"com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.core",
			   				  "com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.persistence",
							  "com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.service"})
public class AppConfig {
	@Bean
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
    }
	
//	@Bean
//	public JdbcTemplate getJdbcTemplate(DataSource datasource) {
//		return new JdbcTemplate(datasource);
//	}

}