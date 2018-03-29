package com.excilys.sramirez.formation.MvnComputerDataBase.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.sramirez.formation.MvnComputerDataBase.DTO.ComputerDTO;
import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Computer;
import com.excilys.sramirez.formation.MvnComputerDataBase.mapper.CompanyMapper;
import com.excilys.sramirez.formation.MvnComputerDataBase.mapper.ComputerMapper;
import com.excilys.sramirez.formation.MvnComputerDataBase.service.ComputerService;

import com.excilys.sramirez.formation.MvnComputerDataBase.service.CompanyService;

import com.excilys.sramirez.formation.MvnComputerDataBase.DTO.CompanyDTO;
import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Company;

@Controller
//@WebServlet("/ServletDashboard")
public class ControllerDashboard {

	private static final Logger logger = LogManager.getLogger( ControllerDashboard.class ) ;

	//@Autowired
	private final ComputerService computerService;
	private final CompanyService companyService;

	//@Autowired
	private final ComputerMapper computerMapper;

	private final CompanyMapper companyMapper;

	public ControllerDashboard(ComputerService computerService, ComputerMapper computerMapper, CompanyService companyService, CompanyMapper companyMapper) {
		this.computerMapper = computerMapper;
		this.computerService = computerService;
		this.companyService = companyService;
		this.companyMapper = companyMapper;
	}

	int firstPage = 1;
	int nbEltsPerPage =  10;
	int firstLocalisationPages = 1;
	int nbAcessiblePages = 5;


	@SuppressWarnings("unchecked")
	public void listComputersFilterOrOrderedOrNot(ModelMap model, @RequestParam Map<String, String> params, int page){
		ArrayList<ComputerDTO> list = new ArrayList<ComputerDTO>();
		if(params.get("filter") != null && params.get("filter") != "") {
			System.out.println("va t on filtrer?");
			String filter = params.get("filter");
			model.addAttribute("filter", filter);
			//System.out.println("filter = "+filter);
			for (Computer c : computerService.listFiltered(page, nbEltsPerPage, filter) ) {
				list.add(computerMapper.toDTO(c));
			}
		}else if(params.get("orderType") != null && params.get("orderType") != "") {
			String orderType = params.get("orderType");
			model.addAttribute("orderType", orderType);
			System.out.println("orderType = "+orderType);
			for (Computer c : computerService.listOrdered(page, nbEltsPerPage, orderType) ) {
				list.add(computerMapper.toDTO(c));
			}
		}else {
			for (Computer c : computerService.list(page, nbEltsPerPage) ) {  
				list.add(computerMapper.toDTO(c));
			}
		}

		model.addAttribute("listComputers", list);
	}	

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @request requete.
	 * @response reponse.
	 */
	@GetMapping("dashboard")
	public String dashboardGet(ModelMap model, @RequestParam Map<String, String> params) throws IOException {
		System.out.println("gettttttttttttttttttttttttttttttttttttttt");

		model.addAttribute("nbAcessiblePages", nbAcessiblePages);

		//le nombre de computers et de pages
		int nbCompu = computerService.count();
		int maxPage;
		if(nbCompu % nbEltsPerPage != 0) {
			maxPage = (nbCompu / nbEltsPerPage) + 1;
		}else {
			maxPage = (nbCompu / nbEltsPerPage);
		}
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("nbCompu", nbCompu);		

		//on va passer les liens vers les pages suivantes à la jsp
		int page;
		if (params.get("page") != null) {
			page = Integer.valueOf(params.get("page"));
		} else {
			page = firstPage;
		}
		model.addAttribute("page", page);
		listComputersFilterOrOrderedOrNot(model, params, page);

		//pour le next page et le prévious page
		int localisationPages;
		int localisationNext = 0;

		if (params.get("localisationNext") != null ) {
			localisationNext = Integer.valueOf(params.get("localisationNext"));
		} 
		model.addAttribute("localisationNext", localisationNext);

		if (params.get("localicationPages") != null && !params.get("localicationPages").equals("")) {

			System.out.println( Integer.parseInt(params.get("localisationPages")) );
			localisationPages = Integer.valueOf(params.get("localisationPages"));
		} else {
			localisationPages = firstLocalisationPages;
		}
		localisationPages+=localisationNext;
		model.addAttribute("localisationPages", localisationPages);

		return "dashboard";
		//this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,  response);
	}

	@PostMapping("dashboard")
	protected String dashboardPost(ModelMap model, @RequestParam Map<String, String> params) throws IOException {
		System.out.println("post");

		String selection = (String) params.get("selection");

		if (selection != null) {
			String[] toDeleteList = selection.split(",");

			for (String toDelete : toDeleteList) {
				int id = Integer.parseInt(toDelete);
				computerService.delete(id);
			}
			listComputersFilterOrOrderedOrNot(model, params, firstPage);
		}
		return "dashboard";
		//response.sendRedirect("ServletDashboard");
	}

	@GetMapping("add")
	public String addGet(ModelMap model, @RequestParam Map<String, String> params) throws IOException {

		ArrayList<CompanyDTO> listCompanyDTO = new ArrayList<CompanyDTO>();

		for ( Company c : companyService.list() ) {
			listCompanyDTO.add(companyMapper.toDTO(c));
		}
		model.addAttribute("listCompany", listCompanyDTO);

		return "addComputer";
		//this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,  response);
	}



	@PostMapping("add")
	protected String addPost(ModelMap model, @RequestParam Map<String, String> params) throws IOException {

		ComputerDTO compuDTO = new ComputerDTO();

		String name = params.get("computerName");
		String introduced = params.get("introduced");
		String discontinued = params.get("discontinued");
		compuDTO.setName(name);
		compuDTO.setIntroduced(introduced);
		compuDTO.setDiscontinued(discontinued);

		int companyId = 0;
		String companyName = "";
		if(params.get("companyId") != null) {
			companyId = Integer.parseInt( params.get("companyId") );
			companyName = companyService.getName(companyId);
			compuDTO.setCompanyId(companyId);
			compuDTO.setCompanyName(companyName);
		}	
		Computer c = computerMapper.fromDTO(compuDTO);

		//changer le service pour que si certains atributs du computer sont à null, ca marche quand meme (on va passer un computer directement en parametre du service et on gere les null autre part)
		System.out.println(c.toString());
		System.out.println(computerService);
		computerService.create(c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany());

		return "addComputer";
		//this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,  response);
	}
	
	@GetMapping("edit")
	public String editGet(ModelMap model, @RequestParam Map<String, String> params) throws IOException {
		
		int idCompu = Integer.parseInt( params.get("id") );
		model.addAttribute("id", idCompu);
		Computer compuReferent = computerService.returnComp(idCompu);
		ComputerDTO compuReferentDTO = computerMapper.toDTO(compuReferent);
		model.addAttribute("compuReferentDTO", compuReferentDTO);
		
		ArrayList<CompanyDTO> listCompanyDTO = new ArrayList<CompanyDTO>();

        for ( Company c : companyService.list() ) {
            listCompanyDTO.add(companyMapper.toDTO(c));
        }
        model.addAttribute("listCompany", listCompanyDTO);
		
		//this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request,  response);
        return "editComputer";
	}


	@PostMapping("edit")
	public String doPost(ModelMap model, @RequestParam Map<String, String> params) throws IOException {
		
		int idCompu = Integer.parseInt( params.get("id") );
		model.addAttribute("id", idCompu);
		
		ComputerDTO compuDTO = new ComputerDTO();
		String name = params.get("computerName");
		String introduced = params.get("introduced");
		String discontinued = params.get("discontinued");
		compuDTO.setName(name);
		compuDTO.setIntroduced(introduced);
		compuDTO.setDiscontinued(discontinued);
		
		int companyId = 0;
		String companyName = "";
		if(params.get("companyId") != null) {
			companyId = Integer.parseInt( params.get("companyId") );
			companyName = companyService.getName(companyId);
			compuDTO.setCompanyId(companyId);
			compuDTO.setCompanyName(companyName);
		}	

		Computer computerUpdated = computerMapper.fromDTO(compuDTO);
		computerUpdated.setId(idCompu);
		
		computerService.update(computerUpdated);
		listComputersFilterOrOrderedOrNot(model, params, firstPage);

		
		return "dashboard";
		//response.sendRedirect("ServletDashboard");
		//this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request,  response);
	}



	//	@Override
	//	public void init(ServletConfig config) throws ServletException {
	//		super.init(config);
	//		ServletContext servletContext = config.getServletContext();
	//		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	//	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	//	    autowireCapableBeanFactory.autowireBean(this);
	//	}

}