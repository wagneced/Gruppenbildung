package ch.zhaw.springboot.restcontroller;

import java.util.ArrayList;
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

    @Autowired
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
            List<Course> courses = new ArrayList<Course>();
            if (personRequest.courseIds != null && personRequest.courseIds.size() > 0) {
                courses = this.courseRepository.findAllById(personRequest.courseIds);
            }
            result = new Person(personRequest.name, personRequest.email, personRequest.zhawId);
            for (Course course : courses) {
                result.addToCourse(course);
            }
            result = this.repository.save(result);
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
            return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "persons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePersonDetails(@PathVariable("id") long id) {
        try {
            this.repository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
