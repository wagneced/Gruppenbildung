package ch.zhaw.springboot.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.springboot.entities.Expense;
import ch.zhaw.springboot.entities.Person;
import ch.zhaw.springboot.repositories.ExpenseRepository;

@RestController
public class ExpenseRestController {
	
	@Autowired
	private ExpenseRepository repository;
	
	@RequestMapping(value = "expenses/expenses", method = RequestMethod.GET)
	public ResponseEntity<List<Expense>> getExpenses() {
		List<Expense> result = this.repository.findAll();
		
		if(!result.isEmpty()) {
			return new ResponseEntity<List<Expense>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Expense>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "expenses/expenses/{id}", method = RequestMethod.GET)
	public ResponseEntity<Expense> getExpenseById(@PathVariable("id") long id) {
		Optional<Expense> result = this.repository.findById(id);
		
		if(result.isPresent()) {
			return new ResponseEntity<Expense>(result.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Expense>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "expenses/expenses/personsbycategory/{category}", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getPersonsByCategory(@PathVariable("category") String category) {
		
		if(this.repository.countByWhatStartingWith(category) < 1) {
			return new ResponseEntity<List<Person>>(HttpStatus.NOT_FOUND);
		}
		
		List<Person> result = this.repository.getPersonByCategory(category);
		
		return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
	}
	
	@PostMapping("expenses/expenses")
	public ResponseEntity<Expense> createExpense(@RequestBody Expense newExpense) {
		Expense result = this.repository.save(newExpense);
		return new ResponseEntity<Expense>(result, HttpStatus.OK);
	}
}
