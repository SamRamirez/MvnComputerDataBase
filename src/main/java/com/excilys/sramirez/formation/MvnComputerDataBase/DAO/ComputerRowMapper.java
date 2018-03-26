package com.excilys.sramirez.formation.MvnComputerDataBase.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Computer;
import com.excilys.sramirez.formation.MvnComputerDataBase.mapper.ComputerMapper;

@Component
public class ComputerRowMapper implements RowMapper<Computer>{

	@Override
	public Computer mapRow(ResultSet rs, int tuple) throws SQLException {		
		return ComputerMapper.fromResultSetToModel(rs);
	}

}
