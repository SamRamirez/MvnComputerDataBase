package com.excilys.sramirez.formation.MvnComputerDataBase.bean;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "computer")
public class Computer extends Element{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column
	private LocalDate introduced;
	@Column
	private LocalDate discontinued;
	@ManyToOne()
	@JoinColumn(name = "company_id")
	private Company company = new Company();
	
//eager/fetch/lazy


	public Computer(ComputerBuilder computerBuilder) {

		this.setId(computerBuilder.id);
		this.setName(computerBuilder.name);
		this.setIntroduced(computerBuilder.introduced);
		this.setDiscontinued(computerBuilder.discontinued);
		this.setCompany(computerBuilder.company);

	}
    
    
    /**
     * Pattern Builder.
     * @author excilys
     *
     */
    public static class ComputerBuilder {
        
        int id;
        String name;
        LocalDate introduced;
        LocalDate discontinued;
        Company company;
        
        public ComputerBuilder() {
            this.name = "default";
            this.id = 0;
        }
        
        public ComputerBuilder withId(int id) {
            this.id = id;
            return this;
        }
        
        public ComputerBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public ComputerBuilder withDateIntro(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }
        
        public ComputerBuilder withDateDisc(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }
        
        public ComputerBuilder withCompany(Company company) {
            this.company = company;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
         
    }
    
    

	public Computer() {
	}
	
	//C'est dans les constructeurs qu'on va éventuellement demander à ce que les champs soient non null
	public Computer(String name) {
		this.name=name;
	}
	
	
	public Computer(String name , LocalDate introduced , int companyId ) {
		this.name = name ;
		this.introduced = introduced ;
		this.company.setId(companyId);
	}
	
	public Computer(String name , LocalDate introduced , LocalDate discontinued , int companyId ) {
		this.name = name ;
		this.introduced = introduced ;
		this.discontinued = discontinued ;
		this.company.setId(companyId);
	}
	
	public Computer(int id, String name , LocalDate introduced , LocalDate discontinued , Company company ) {
		this.id=id;
		this.name = name ;
		this.introduced = introduced ;
		this.discontinued = discontinued ;
		this.company = company; 
	}
	
	public Computer(int id, String name , LocalDate introduced , LocalDate discontinued , int company_id ) {
		this.id=id;
		this.name = name ;
		this.introduced = introduced ;
		this.discontinued = discontinued ;
		this.company.setId(company_id); 
	}
	
	public Computer(String name , LocalDate introduced , LocalDate discontinued, Company company) {
		this.name = name ;
		this.introduced = introduced ;
		this.discontinued = discontinued ;
		this.company = company;
	}


	
	
	
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + company.getId() + "]";
	}
	
	
	
}
