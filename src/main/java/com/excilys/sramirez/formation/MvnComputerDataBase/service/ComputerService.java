package com.excilys.sramirez.formation.MvnComputerDataBase.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.sramirez.formation.MvnComputerDataBase.DAO.ComputerDAO;
import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Company;
import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Computer;
import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Computer.ComputerBuilder;

@Service
public class ComputerService {
	

	private final ComputerDAO compDAO;
	
	private ComputerService(ComputerDAO compDAO) {
		this.compDAO = compDAO;
	}

	public int count() {
		return compDAO.count();
	}
	
	public List<Computer> list(int page, int numberOfElements) {
		return compDAO.list(page, numberOfElements);
	}
	
	public List<Computer> listFiltered(int page, int numberOfElements, String filter){
		return compDAO.listFiltered(page, numberOfElements, filter);
	}
	
	public Computer create(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		Computer comp;
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder();
		
		if (name != "") {
			computerBuilder.withName(name);
		}
		if (introduced != null) {
			computerBuilder.withDateIntro(introduced);
		} 
		if (discontinued != null) {
			computerBuilder.withDateDisc(discontinued);
		} 
		
		if (company != null) {
			computerBuilder.withCompany(company);
		}
		comp = computerBuilder.build();
		
		compDAO.create(comp);			
		return comp;
	}
	
	public Computer info(int id) {
		Computer toReturn = compDAO.info(id).orElse(new Computer());
		System.out.println(toReturn.toString());
		return toReturn;		
	}
	
	public Computer returnComp(int id) {
		Computer toReturn = compDAO.info(id).orElse(new Computer());
		return toReturn;		
	}
	
	public Computer update(Computer comp) {
		compDAO.update(comp);
		return comp;		
	}
	
	public void delete(int id) {
		compDAO.delete(id);
	}
	
	public List<Computer> listOrdered(int page, int numberOfElements, String orderBy){
		return compDAO.listOrdered(page, numberOfElements, orderBy);
		
	}


}
