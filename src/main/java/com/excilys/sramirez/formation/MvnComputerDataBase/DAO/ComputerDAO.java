package com.excilys.sramirez.formation.MvnComputerDataBase.DAO;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Computer;
 


@Repository
public class ComputerDAO {
	
	private static final Logger logger = LogManager.getLogger(ComputerDAO.class);
	
	private final JdbcTemplate jdbcTemplate;  
	private final ComputerRowMapper computerRowMapper;
	
	
	private ComputerDAO(JdbcTemplate jdbcTemplate, ComputerRowMapper computerRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.computerRowMapper = computerRowMapper;
	}

	String queryCountComputers =  "SELECT COUNT(*) from computer";
	String queryListComputers = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id LIMIT ?, ?";
	String queryCreateComputer = "INSERT INTO computer (name, introduced, discontinued, company_id)  VALUES (?, ?, ?, ?)";
	String queryInfoComputer = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name from computer LEFT JOIN company ON computer.company_id=company.id where computer.id=?";
	String queryUpdateComputer = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	String queryDeleteComputer = "DELETE from computer WHERE id = ?";
	String queryListComputersFiltered = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.name LIKE ? LIMIT ?, ? ";
	String queryListComputersOrdered = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id ORDER BY %s ASC LIMIT ?, ? ";
	
	public int count() {
		return jdbcTemplate.queryForObject(queryCountComputers, Integer.class);
	}
	
	public List<Computer> list(int page, int numberOfElements){
		return jdbcTemplate.query(queryListComputers, new Object[] {10*(page-1), numberOfElements}, computerRowMapper);
	}
	
	public List<Computer> listFiltered(int page, int numberOfElements, String filter){
		return jdbcTemplate.query(queryListComputersFiltered, new Object[] {"%"+filter+"%", 10*(page-1), numberOfElements}, computerRowMapper);
	}
	
	public List<Computer> listOrdered(int page, int numberOfElements, String orderBy){
		return jdbcTemplate.query(String.format(queryListComputersOrdered, orderBy), new Object[] {10*(page-1), numberOfElements}, computerRowMapper);
	}

	public void create(Computer comp) {
		jdbcTemplate.update(queryCreateComputer, comp.getName(), comp.getIntroduced(), comp.getDiscontinued(), comp.getCompany());
	}
	
	public Optional<Computer> info(int id) {
		return Optional.ofNullable(jdbcTemplate.queryForObject(queryInfoComputer, new Object[] {id}, computerRowMapper));
	}

	public Computer update(Computer comp) {
		return jdbcTemplate.queryForObject(queryUpdateComputer, new Object[] {comp.getName(), Date.valueOf(comp.getIntroduced()), Date.valueOf(comp.getDiscontinued()), comp.getCompany().getId(), comp.getId()}, computerRowMapper);
	}
	
	public void delete(int id) {
		jdbcTemplate.update(queryDeleteComputer, id);
	}

}
