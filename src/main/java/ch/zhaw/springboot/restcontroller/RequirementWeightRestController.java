package ch.zhaw.springboot.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.springboot.entities.GroupRequirement;
import ch.zhaw.springboot.entities.RequirementWeight;
import ch.zhaw.springboot.entities.Skill;
import ch.zhaw.springboot.model.RequirementWeightRequest;
import ch.zhaw.springboot.repositories.GroupRequirementRepository;
import ch.zhaw.springboot.repositories.RequirementWeightRepository;
import ch.zhaw.springboot.repositories.SkillRepository;

@RestController
@CrossOrigin
public class RequirementWeightRestController {
    @Autowired
    private RequirementWeightRepository repository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private GroupRequirementRepository groupRequirementRepository;
    
    @RequestMapping(value = "grouprequirement", method = RequestMethod.GET)
    public ResponseEntity<List<RequirementWeight>> getRequirementWeightOfGroupRequirement(@RequestBody long id) {
        try {
            GroupRequirement groupRequirement = groupRequirementRepository.findById(id).get();
            List<RequirementWeight> result = groupRequirement.getRequirementWeights();
            return new ResponseEntity<List<RequirementWeight>>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<RequirementWeight>>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<List<RequirementWeight>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "grouprequirement", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateRequirementWeightOfGroupRequirement(@RequestBody List<RequirementWeightRequest> requirementWeightRequests) {
        try {
            RequirementWeight requirementWeight;
            for(RequirementWeightRequest requirementWeightRequest : requirementWeightRequests) {
                if(requirementWeightRequest.id > 0) {
                    requirementWeight = repository.findById(requirementWeightRequest.id).get();
                    requirementWeight.setRating(requirementWeightRequest.weight);
                    repository.save(requirementWeight);
                }
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "grouprequirement", method = RequestMethod.POST)
    public ResponseEntity<Void> createRequirementWeight(@RequestBody RequirementWeightRequest request) {
        try {
            Skill skill = skillRepository.findById(request.skillId).get();
            GroupRequirement requirement = groupRequirementRepository.findById(request.groupRequirementId).get();
            RequirementWeight requirementWeight = new RequirementWeight(request.weight,requirement,skill);
            requirementWeight.setRating(request.weight);
            repository.save(requirementWeight);            
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
