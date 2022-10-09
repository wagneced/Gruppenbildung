package ch.zhaw.springboot.entities;

import javax.persistence.Entity;

@Entity
public class Student extends Person{

		private String leginr;
		
		public Student(String name, long birthdate, String leginr) {
			super(name, birthdate);
			this.leginr = leginr;
		}
		
		public Student() {
			super();
		}
		
		public String getLeginr() {
			return this.leginr;
		}
}
