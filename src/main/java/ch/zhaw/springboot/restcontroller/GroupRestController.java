package ch.zhaw.springboot.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.springboot.entities.Course;
import ch.zhaw.springboot.entities.Group;
import ch.zhaw.springboot.entities.GroupRequirement;
import ch.zhaw.springboot.entities.Person;
import ch.zhaw.springboot.repositories.CourseRepository;
import ch.zhaw.springboot.repositories.GroupRepository;

@RestController
@CrossOrigin
public class GroupRestController {
    @Autowired
    private GroupRepository repository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @RequestMapping(value = "courses/{id}/groups", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> findAllGroupsOfCourse(@PathVariable("id") long id) {
        List<Group> result = this.repository.findAllGroupsOfCourse(id);
        return new ResponseEntity<List<Group>>(result,HttpStatus.OK);        
    }
    
    //Currently just a simple solution for group creation without any deep logic
    @RequestMapping(value = "courses/{id}/groups", method = RequestMethod.POST)
    public ResponseEntity<List<Group>> generateGroups(@PathVariable("id") long id) {
        try {
            Course course = this.courseRepository.findById(id).get();
            cleanCourse(course);
            List<Person> attendees = course.getAttendees();
            GroupRequirement groupRequirement = course.getGroupRequirement();
            int size = groupRequirement.getGroupSize();
            List<Group> groups = new ArrayList<Group>();
        
            for (int i = 0; i < attendees.size(); i++) {
                if((i % size) >= groups.size()) {
                    //index does not exist => Group has to be created
                    groups.add(this.repository.save(new Group(course)));
                }
                groups.get(i % size).addMember(attendees.get(i));
            }
            List<Group> result = this.repository.saveAll(groups);
            courseRepository.save(course);
            return new ResponseEntity<List<Group>>(result,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Group>>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<List<Group>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private void cleanCourse(Course course) {
        for(Group group: course.getGroups()) {
            repository.delete(group);
        }
    }
}
