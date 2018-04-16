package com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.persistence.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.config.HibernateConfig;
import com.excilys.sramirez.formation.MvnComputerDataBaseMultiModule.core.bean.Company;

@Repository
public class CompanyDAO {

//	private final JdbcTemplate jdbcTemplate;
//	private final CompanyRowMapper companyRowMapper;
//
//	private CompanyDAO(JdbcTemplate jdbcTemplate, CompanyRowMapper companyRowMapper) {
//		this.jdbcTemplate = jdbcTemplate;
//		this.companyRowMapper=companyRowMapper;
//	}

	String hqlList = "select company from Company company";
	String hqlInfo = "SELECT company from Company company where computer.id = :id";


//	String queryListCompanyFull = "select id, name from company";
//	String queryListCompany = "select id, name from company LIMIT ?, ?";
//	String queryNameCompany = "SELECT name from company WHERE id = ?";


	public List<Company> list(){
		List<Company> listCompanies = new ArrayList<Company>();
		try(Session session = HibernateConfig.getSessionFactory().openSession();){
			Query<Company> query =session.createQuery(hqlList, Company.class);
			listCompanies = query.list();
		}
		return listCompanies;
	}


	public String getName(int id){		
		try(Session session = HibernateConfig.getSessionFactory().openSession();){
			Query<Company> query = session.createQuery(hqlInfo, Company.class);
			query.setParameter("id", id);
			Company reponse = query.uniqueResult();
			return reponse.getName();
		}
	}

}
