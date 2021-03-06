package com.excilys.sramirez.formation.MvnComputerDataBase.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.sramirez.formation.MvnComputerDataBase.DTO.CompanyDTO;
import com.excilys.sramirez.formation.MvnComputerDataBase.DTO.ComputerDTO;
import com.excilys.sramirez.formation.MvnComputerDataBase.mapper.CompanyMapper;
import com.excilys.sramirez.formation.MvnComputerDataBase.mapper.ComputerMapper;
import com.excilys.sramirez.formation.MvnComputerDataBase.service.CompanyService;
import com.excilys.sramirez.formation.MvnComputerDataBase.service.ComputerService;

import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Company;
import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Computer;

//@Controller
@WebServlet("/ServletAddComputer")
public class ServletAddComputer extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger( ServletAddComputer.class ) ;	
	
	@Autowired
	private CompanyService companyService;
	//= CompanyService.getInstance();
	
	@Autowired
	private CompanyMapper companyMapper;
	//= CompanyMapper.getInstance();
	
	@Autowired
	private ComputerMapper computerMapper;
	//= ComputerMapper.getInstance();
	
	@Autowired
	private ComputerService computerService;
	
	//= ComputerService.getInstance();
	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<CompanyDTO> listCompanyDTO = new ArrayList<CompanyDTO>();
		
		System.out.println(companyService);

        for ( Company c : companyService.list() ) {
            listCompanyDTO.add(companyMapper.toDTO(c));
        }
        request.setAttribute("listCompany", listCompanyDTO);

		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,  response);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ComputerDTO compuDTO = new ComputerDTO();
		
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		compuDTO.setName(name);
		compuDTO.setIntroduced(introduced);
		compuDTO.setDiscontinued(discontinued);
		
		int companyId = 0;
		String companyName = "";
		if(request.getParameter("companyId") != null) {
			companyId = Integer.parseInt( request.getParameter("companyId") );
			companyName = companyService.getName(companyId);
			compuDTO.setCompanyId(companyId);
			compuDTO.setCompanyName(companyName);
		}	
		
		
		Computer c = computerMapper.fromDTO(compuDTO);
		
		//changer le service pour que si certains atributs du computer sont à null, ca marche quand meme (on va passer un computer directement en parametre du service et on gere les null autre part)
		System.out.println(c.toString());
		System.out.println(computerService);
		computerService.create(c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany());
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,  response);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	    autowireCapableBeanFactory.autowireBean(this);
	}


	
}
