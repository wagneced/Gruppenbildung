package ch.zhaw.springboot.entities;

import javax.persistence.Entity;

@Entity
public class Lecturer extends Person{
	
	private String office;
	
	public Lecturer(String name, long birthdate, String office) {
		super(name, birthdate);
		this.office = office;
	}
	
	public Lecturer() {
		super();
	}
	
	public String getOffice() {
		return this.office;
	}
}
