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
import ch.zhaw.springboot.entities.GroupRequirement;
import ch.zhaw.springboot.entities.Person;
import ch.zhaw.springboot.model.CourseRequest;
import ch.zhaw.springboot.repositories.CourseRepository;
import ch.zhaw.springboot.repositories.GroupRequirementRepository;
import ch.zhaw.springboot.repositories.PersonRepository;

@RestController
@CrossOrigin
public class CourseRestController {
    @Autowired
    private CourseRepository repository;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private GroupRequirementRepository groupRequirementRepository;
    
    @RequestMapping(value = "courses/{courseId}/persons/{personId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> addAttendeeToCourse(@PathVariable("courseId") long courseId, @PathVariable("personId") long personId) {
        try {
            Course course = this.repository.findById(courseId).get();
            Person person = this.personRepository.findById(personId).get();
            course.addAttendee(person);
            this.repository.save(course);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "courses/{id}/persons", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllCourseAttendees(@PathVariable("id") long id) {
        Optional<Course> course = this.repository.findById(id);
        
        if (course.isPresent()) {
            List<Person> result = course.get().getAttendees();
            return new ResponseEntity<List<Person>> (result, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Person>> (HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "courses/active", method = RequestMethod.GET)
    public ResponseEntity<List<Course>> getAllActiveCourses() {
        List<Course> result = this.repository.findAllActiveCourses();
        return new ResponseEntity<List<Course>>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "courses", method = RequestMethod.GET)
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> result = this.repository.findAll();
        return new ResponseEntity<List<Course>>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "courses/{id}", method = RequestMethod.GET)
    public ResponseEntity<Course> getCourse(@PathVariable("id") long id) {
        Optional<Course> result = this.repository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<Course> (result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Course> (HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "courses/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Long> updateCourse(@RequestBody CourseRequest request) {
        try {
            Course course = this.repository.findById(request.id).get();
            course.setCourseState(request.courseActive);
            course.setName(request.name);
            Optional<GroupRequirement> optRequirement = this.groupRequirementRepository.findById(request.groupRequirementId);
            if(optRequirement.isPresent()) {
                GroupRequirement requirement = optRequirement.get();
                course.setGroupRequirement(requirement);
            }
            Course result = this.repository.save(course);
            return new ResponseEntity<Long>(result.getId(),HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "courses/{courseId}/persons/{personId}/remove", method = RequestMethod.PUT)
    public ResponseEntity<Void> removeCourseMemeber(@PathVariable("courseId") long courseId, @PathVariable("personId") long personId) {
        try {
            Course course = this.repository.findById(courseId).get();
            Person person = this.personRepository.findById(personId).get();
            course.removeAttendee(person);
            this.repository.save(course);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "courses/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") long id) {
        try {
            this.repository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
