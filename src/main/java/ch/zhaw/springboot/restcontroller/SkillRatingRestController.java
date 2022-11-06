package ch.zhaw.springboot.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.springboot.entities.Person;
import ch.zhaw.springboot.entities.Skill;
import ch.zhaw.springboot.entities.SkillRating;
import ch.zhaw.springboot.model.SkillRatingRequest;
import ch.zhaw.springboot.repositories.PersonRepository;
import ch.zhaw.springboot.repositories.SkillRatingRepository;
import ch.zhaw.springboot.repositories.SkillRepository;

@RestController
@CrossOrigin
public class SkillRatingRestController {
    @Autowired
    private SkillRatingRepository repository;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    @RequestMapping(value = "persons/{id}/skills/rating", method = RequestMethod.GET)
    public ResponseEntity<List<Skill>> getSkillsRequiredToBeRatedByPerson(@PathVariable("id") long id) {
        try {
            List<Skill> result = this.repository.findSkillsRequiredToBeRatedByPerson(id);
            return new ResponseEntity<List<Skill>>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Skill>>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<List<Skill>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "persons/{id}/skills", method = RequestMethod.GET)
    public ResponseEntity<List<SkillRating>> getSkillRatingsOfPerson(@PathVariable("id") long id) {
        try {
            Person person = this.personRepository.findById(id).get();
            List<SkillRating> result = person.getRatedSkills();
            return new ResponseEntity<List<SkillRating>>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<SkillRating>>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<List<SkillRating>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "persons/{id}/skills", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateSkillRatingsOfPerson(@PathVariable("id") long id, @RequestBody List<SkillRatingRequest> skillRatingRequests) {
        try {
            SkillRating skillRating;
            Skill skill;
            Person person = this.personRepository.findById(id).get();
            for(SkillRatingRequest skillRatingRequest : skillRatingRequests) {
                if(skillRatingRequest.id > 0) {
                    skillRating = repository.findById(skillRatingRequest.id).get();
                    skillRating.setRating(skillRatingRequest.rating);
                } else {
                    skill = skillRepository.findById(skillRatingRequest.skillId).get();
                    skillRating = new SkillRating(skillRatingRequest.rating, person, skill);
                }
                this.repository.save(skillRating);
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
