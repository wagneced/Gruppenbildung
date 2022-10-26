package ch.zhaw.springboot.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.springboot.entities.Course;
import ch.zhaw.springboot.entities.Person;
import ch.zhaw.springboot.model.PersonRequest;
import ch.zhaw.springboot.repositories.CourseRepository;
import ch.zhaw.springboot.repositories.PersonRepository;

@RestController
@CrossOrigin
public class PersonRestController {
    @Autowired
    private PersonRepository repository;
    
    private CourseRepository courseRepository;
    
    @RequestMapping(value = "persons", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> result = this.repository.findAll();
        return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPersonDetails(@PathVariable("id") long id) {
        Optional<Person> result = this.repository.findById(id);

        if (result.isPresent()) {
            return new ResponseEntity<Person>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "persons", method = RequestMethod.POST)
    public ResponseEntity<Person> createPerson(@RequestBody PersonRequest personRequest) {
        try {
            Person result = null;
            Optional<Course> course = Optional.empty();
            if(personRequest.courseId > 0) {
                course = this.courseRepository.findById(personRequest.courseId);
            }
            if(course.isPresent()) {
                result = this.repository
                        .save(new Person(personRequest.name, personRequest.email, personRequest.zhawId, course.get()));
            } else {
                result = this.repository
                        .save(new Person(personRequest.name, personRequest.email, personRequest.zhawId));
            }
            result = this.repository
                    .save(new Person(personRequest.name, personRequest.email, personRequest.zhawId));
            return new ResponseEntity<Person>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "persons", method = RequestMethod.PUT)
    public ResponseEntity<Long> updatePerson(@RequestBody PersonRequest personRequest) {
        try {
            Person person = this.repository.findById(personRequest.id).get();
            person.setName(personRequest.name);
            person.setEmail(personRequest.email);
            person.setZhawId(personRequest.zhawId);
            
            
            Person result = this.repository.save(person);
            return new ResponseEntity<Long>(result.getId(), HttpStatus.OK);
            
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
