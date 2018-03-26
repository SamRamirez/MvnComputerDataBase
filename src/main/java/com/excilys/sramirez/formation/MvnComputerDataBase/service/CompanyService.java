package com.excilys.sramirez.formation.MvnComputerDataBase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.sramirez.formation.MvnComputerDataBase.DAO.CompanyDAO;
import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Company;

@Service
public class CompanyService {
	private final CompanyDAO companyDAO;
	
	private CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public List<Company> list() {
		return companyDAO.list();
	}

	public String getName(int companyId) {
		return companyDAO.getName(companyId);
	}

}
