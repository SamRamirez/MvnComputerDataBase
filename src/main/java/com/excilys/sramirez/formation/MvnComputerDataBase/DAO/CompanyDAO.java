package com.excilys.sramirez.formation.MvnComputerDataBase.DAO;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Company;

@Repository
public class CompanyDAO {

	private final JdbcTemplate jdbcTemplate;
	private final CompanyRowMapper companyRowMapper;
	
	private CompanyDAO(JdbcTemplate jdbcTemplate, CompanyRowMapper companyRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.companyRowMapper=companyRowMapper;
	}


	String queryListCompanyFull = "select id, name from company";
	String queryListCompany = "select id, name from company LIMIT ?, ?";
	String queryNameCompany = "SELECT name from company WHERE id = ?";


	 public List<Company> list(){
			return jdbcTemplate.query(queryListCompanyFull, companyRowMapper); 
	}
	
	
	public String getName(int id){						
		return jdbcTemplate.queryForObject(queryNameCompany, new Object[] {id}, String.class);
}

}
