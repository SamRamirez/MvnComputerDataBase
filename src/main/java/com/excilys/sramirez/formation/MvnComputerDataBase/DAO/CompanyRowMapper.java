package com.excilys.sramirez.formation.MvnComputerDataBase.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Company;

@Component
public class CompanyRowMapper implements RowMapper<Company>{
	
	@Override
	public Company mapRow(ResultSet rs, int tuple) throws SQLException {
		return new Company(rs.getInt("id"), rs.getString("name"));
	}
	
}
