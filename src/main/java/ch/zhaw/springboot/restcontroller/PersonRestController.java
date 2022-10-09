package ch.zhaw.springboot.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.springboot.entities.Person;
import ch.zhaw.springboot.repositories.PersonRepository;

@RestController
public class PersonRestController {
	@Autowired
	private PersonRepository repository;

	@RequestMapping(value = "expenses/persons", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getPersons() {
		List<Person> result = this.repository.findAll();

		if (!result.isEmpty()) {
			return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Person>>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "expenses/persons/{id}", method = RequestMethod.GET)
	public ResponseEntity<Person> getPersonById(@PathVariable("id") long id) {
		Optional<Person> result = this.repository.findById(id);
		if (result.isPresent()) {
			return new ResponseEntity<Person>(result.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "expenses/persons/bybirthdate/{birthdate}", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getPersonsByBirthdate(@PathVariable("birthdate") long birthdate) {
		List<Person> result = this.repository.findPersonsByBirthdate(birthdate);

		if (!result.isEmpty()) {
			return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Person>>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "expenses/persons/{id}/{category}", method = RequestMethod.GET)
	public ResponseEntity<Double> getTotalExpensesByCategory(@PathVariable("id") long personId,
			@PathVariable("category") String category) {
		Optional<Person> person = this.repository.findById(personId);
		if (!person.isPresent()) {
			return new ResponseEntity<Double>(HttpStatus.NOT_FOUND);
		}
		
		double result = person.get().getTotalExpensesByCategory(category);
		return new ResponseEntity<Double>(result, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "expenses/persons", method = RequestMethod.POST)
	public ResponseEntity<Person> createPerson(@RequestBody Person newPerson) {
		// Test for correct data / SQL Injection
		Person result = this.repository.save(newPerson);
		return new ResponseEntity<Person>(result, HttpStatus.OK);
	}
}
