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

import ch.zhaw.springboot.entities.Skill;
import ch.zhaw.springboot.model.SkillRequest;
import ch.zhaw.springboot.repositories.SkillRepository;

@RestController
@CrossOrigin
public class SkillRestController {
    @Autowired
    private SkillRepository repository;
    
    @RequestMapping(value = "skills", method = RequestMethod.GET)
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> result = this.repository.findAll();

        if (result == null || result.isEmpty()) {
            return new ResponseEntity<List<Skill>>(result, HttpStatus.OK);
        }
        
        return new ResponseEntity<List<Skill>>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "skills/{id}", method = RequestMethod.GET)
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") long id) {
        Optional<Skill> result = this.repository.findById(id);

        if (result.isPresent()) {
            return new ResponseEntity<Skill>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Skill>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "skills", method = RequestMethod.POST)
    public ResponseEntity<Skill> createNewSkill(@RequestBody SkillRequest skillRequest) {
        try {
            Skill result = this.repository
                    .save(new Skill(skillRequest.name, skillRequest.description));
            return new ResponseEntity<Skill>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Skill>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "skills", method = RequestMethod.PUT)
    public ResponseEntity<Long> updateSkill(@RequestBody SkillRequest skillRequest) {
        try {
            Skill skill = this.repository.findById(skillRequest.id).get();
            
            skill.setName(skillRequest.name);
            skill.setDescription(skillRequest.description);
            
            Skill result = this.repository.save(skill);
            return new ResponseEntity<Long>(result.getId(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "skills/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSkill(@PathVariable("id") long id) {
        try {
            Skill skill = this.repository.findById(id).get();
            this.repository.delete(skill);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
