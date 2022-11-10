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
import ch.zhaw.springboot.model.GroupRequirementRequest;
import ch.zhaw.springboot.repositories.CourseRepository;
import ch.zhaw.springboot.repositories.GroupRequirementRepository;

@RestController
@CrossOrigin
public class GroupRequirementRestController {
    @Autowired
    private GroupRequirementRepository repository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @RequestMapping(value = "grouprequirements", method = RequestMethod.GET)
    public ResponseEntity<List<GroupRequirement>> getAllGroupRequirements() {
        List<GroupRequirement> result = this.repository.findAll();
        return new ResponseEntity<List<GroupRequirement>>(result,HttpStatus.OK);
    }
    
    @RequestMapping(value = "grouprequirements/{id}", method = RequestMethod.GET)
    public ResponseEntity<GroupRequirement> getGroupRequirement(@PathVariable("id") long id) {
        Optional<GroupRequirement> result = this.repository.findById(id);
        if(result.isPresent()) {
            return new ResponseEntity<GroupRequirement>(result.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<GroupRequirement>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "grouprequirements", method = RequestMethod.POST)
    public ResponseEntity<Long> createGroupRequirement(@RequestBody GroupRequirementRequest request) {
        GroupRequirement groupReq = new GroupRequirement(request.name, request.generateEqualGroups, request.groupSize , request.groupByPersonality);
        Optional<Course> course = this.courseRepository.findById(request.courseId);
        if(course.isPresent()) {
            groupReq.addCourse(course.get());
        }
        GroupRequirement result = this.repository.save(groupReq);
        return new ResponseEntity<Long>(result.getId(),HttpStatus.OK);
    }
    
    @RequestMapping(value = "grouprequirements/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateGroupRequirement(@RequestBody GroupRequirementRequest request) {
        try {
            GroupRequirement result = this.repository.findById(request.id).get();
            result.setName(request.name);
            result.setGenerateEqualGroups(request.generateEqualGroups);
            result.setGroupSize(request.groupSize);
            result.setGroupByPersonality(request.groupByPersonality);
        
            repository.save(result);
        
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "grouprequirements/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGroupRequirement(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
