package com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.persistence.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.config.HibernateConfig;
import com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.core.bean.Computer;
 


@Repository
public class ComputerDAO {
	
	private static final Logger logger = LogManager.getLogger(ComputerDAO.class);
	
	//private final JdbcTemplate jdbcTemplate;  
	//private final ComputerRowMapper computerRowMapper;
	
	
//	private ComputerDAO(JdbcTemplate jdbcTemplate, ComputerRowMapper computerRowMapper) {
//		this.jdbcTemplate = jdbcTemplate;
//		this.computerRowMapper = computerRowMapper;
//	}

	String hqlList = "SELECT computer from Computer computer";
	String hqlCount = "SELECT count(*) from Computer";
	String hqlInfo = "SELECT computer from Computer computer where computer.id = :id";
	
//	String queryCountComputers =  "SELECT COUNT(*) from computer";
//	String queryListComputers = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id LIMIT ?, ?";
//	String queryCreateComputer = "INSERT INTO computer (name, introduced, discontinued, company_id)  VALUES (?, ?, ?, ?)";
//	String queryInfoComputer = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name from computer LEFT JOIN company ON computer.company_id=company.id where computer.id=?";
//	String queryUpdateComputer = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
//	String queryDeleteComputer = "DELETE from computer WHERE id = ?";
//	String queryListComputersFiltered = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.name LIKE ? LIMIT ?, ? ";
//	String queryListComputersOrdered = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id ORDER BY %s ASC LIMIT ?, ? ";
	
//	public int count() {
//		return jdbcTemplate.queryForObject(queryCountComputers, Integer.class);
//	}
	
	public int count() {
		try (Session session = HibernateConfig.getSessionFactory().openSession();) {
			Query<Long> query = session.createQuery(hqlCount, Long.class);
			Long reponse = query.uniqueResult();
			return reponse == null? 0 : reponse.intValue();
		} 
	}
	
//	public List<Computer> list(int page, int numberOfElements){
//		return jdbcTemplate.query(queryListComputers, new Object[] {10*(page-1), numberOfElements}, computerRowMapper);
//	}
	
	public List<Computer> list(int page, int numberOfElements){
		List<Computer> listComputers = new ArrayList<Computer>();
		
		try(Session session = HibernateConfig.getSessionFactory().openSession();){
			Query<Computer> query = session.createQuery(hqlList, Computer.class);
			query.setMaxResults(numberOfElements);
			query.setFirstResult(numberOfElements*(page-1));
			listComputers = query.list();
		}
		return listComputers;
	}
	
//	public List<Computer> listFiltered(int page, int numberOfElements, String filter){
//		return jdbcTemplate.query(queryListComputersFiltered, new Object[] {"%"+filter+"%", 10*(page-1), numberOfElements}, computerRowMapper);
//	}
	
	public List<Computer> listFiltered(int page, int numberOfElements, String filter){
		List<Computer> listComputers = new ArrayList<Computer>();

		try(Session session = HibernateConfig.getSessionFactory().openSession();){
			Query<Computer> query = session.createQuery( (hqlList+" WHERE name LIKE :filter"), Computer.class);
			query.setMaxResults(numberOfElements);
			query.setFirstResult(numberOfElements*(page-1));
			query.setParameter("filter", "%"+filter+"%");
			listComputers = query.list();
		}
		return listComputers;	
	}
	
//	public List<Computer> listOrdered(int page, int numberOfElements, String orderBy){
//		return jdbcTemplate.query(String.format(queryListComputersOrdered, orderBy), new Object[] {10*(page-1), numberOfElements}, computerRowMapper);
//	}
	
	public List<Computer> listOrdered(int page, int numberOfElements, String orderBy){
		List<Computer> listComputers = new ArrayList<Computer>();

		try(Session session = HibernateConfig.getSessionFactory().openSession();){
			Query<Computer> query = session.createQuery( String.format(hqlList+" ORDER BY %s Asc", orderBy), Computer.class);
			query.setMaxResults(numberOfElements);
			query.setFirstResult(numberOfElements*(page-1));
			listComputers = query.list();
		}
		return listComputers;	
	}

//	public void create(Computer comp) {
//		jdbcTemplate.update(queryCreateComputer, comp.getName(), comp.getIntroduced(), comp.getDiscontinued(), comp.getCompany().getId());
//	}

	public void create(Computer computer){
		try (Session session = HibernateConfig.getSessionFactory().openSession();) {
			int id = (int) session.save(computer);
			computer.setId(id);
		} 
	}
		
//	public Optional<Computer> info(int id) {
//		return Optional.ofNullable(jdbcTemplate.queryForObject(queryInfoComputer, new Object[] {id}, computerRowMapper));
//	}
	
	public Optional<Computer> info(int id) {
		System.out.println("info de DAO appelé");
		try(Session session = HibernateConfig.getSessionFactory().openSession();){
			Query<Computer> query = session.createQuery(hqlInfo, Computer.class);
			query.setParameter("id", id);
			Computer reponse = query.uniqueResult();
			return Optional.ofNullable(reponse);
		}	
	}
	
	public Computer computerFromId(int id) {
		System.out.println("info de DAO appelé");
		try(Session session = HibernateConfig.getSessionFactory().openSession();){
			Query<Computer> query = session.createQuery(hqlInfo, Computer.class);
			query.setParameter("id", id);
			Computer reponse = query.uniqueResult();
			return reponse;
		}	
	}
	
//	public void update(Computer comp) {
//		//return jdbcTemplate.queryForObject(queryUpdateComputer, new Object[] {comp.getName(), Date.valueOf(comp.getIntroduced()), Date.valueOf(comp.getDiscontinued()), comp.getCompany().getId(), comp.getId()}, computerRowMapper);
//		jdbcTemplate.update(queryUpdateComputer, comp.getName(), Date.valueOf(comp.getIntroduced()), Date.valueOf(comp.getDiscontinued()), comp.getCompany().getId(), comp.getId()/*, computerRowMapper*/);
//	}
	
//	public void update(Computer comp) {
//		try(Session session = HibernateConfig.getSessionFactory().openSession();)  {
//			session.beginTransaction();
//			session.createQuery("UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id")
//					.setParameter("name", comp.getName())
//					.setParameter("introduced", comp.getIntroduced())
//					.setParameter("discontinued", comp.getDiscontinued())
//					.setParameter("company", comp.getCompany().getId() != 0? comp.getCompany().getId() : null)
//					.setParameter("id", comp.getId())
//					.executeUpdate();
//			session.getTransaction().commit();
//		}
//	}
	
	
	public void update(Computer ucomputer) {
		if (this.info(ucomputer.getId()) != null) { 
			try (Session session = HibernateConfig.getSessionFactory().openSession()) {
				session.beginTransaction();
				session.update(ucomputer);
				session.getTransaction().commit();
			}
		} else {
			logger.error("Pas d'ordinateur reçu à mettre à jour");
		}
	}
	
//	public void delete(int id) {
//		jdbcTemplate.update(queryDeleteComputer, id);
//	}
	
//	public void delete(int id) {
//		try(Session session = HibernateConfig.getSessionFactory().openSession();)  {
//			session.beginTransaction();
//			session.createQuery("DELETE from computer WHERE id = :id").setParameter("id", id).executeUpdate();
//			session.getTransaction().commit();
//		}
//	}
	
	public void delete(int id) {
		try(Session session = HibernateConfig.getSessionFactory().openSession();)  {
			session.beginTransaction();
			session.delete(this.computerFromId(id));
			session.getTransaction().commit();
		}
	}

}
