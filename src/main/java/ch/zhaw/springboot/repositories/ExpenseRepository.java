package ch.zhaw.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.zhaw.springboot.entities.Expense;
import ch.zhaw.springboot.entities.Person;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	public int countByWhatStartingWith(String category);
	
	@Query("SELECT p FROM Person p, Expense e WHERE e.what LIKE ?1% AND e MEMBER OF p.expenses")
	public List<Person> getPersonByCategory(String category);
}
