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
    
    @RequestMapping(value = "grouprequirements/{id}/weights", method = RequestMethod.GET)
    public ResponseEntity<List<RequirementWeight>> getRequirementWeightsOfGroupRequirement(@PathVariable("id") long id) {
        try {
            GroupRequirement groupRequirement = this.groupRequirementRepository.findById(id).get();
            List<RequirementWeight> result = groupRequirement.getRequirementWeights();
            return new ResponseEntity<List<RequirementWeight>>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<RequirementWeight>>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<List<RequirementWeight>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "grouprequirements/{id}/weights", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateRequirementWeightOfGroupRequirement(@RequestBody List<RequirementWeightRequest> requirementWeightRequests) {
        try {
            RequirementWeight requirementWeight;
            for(RequirementWeightRequest requirementWeightRequest : requirementWeightRequests) {
                if(requirementWeightRequest.id > 0) {
                    requirementWeight = this.repository.findById(requirementWeightRequest.id).get();
                    requirementWeight.setWeight(requirementWeightRequest.weight);
                    this.repository.save(requirementWeight);
                }
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "grouprequirements/{id}/weights", method = RequestMethod.POST)
    public ResponseEntity<Void> createRequirementWeight(@RequestBody RequirementWeightRequest request) {
        try {
            Skill skill = this.skillRepository.findById(request.skillId).get();
            GroupRequirement requirement = groupRequirementRepository.findById(request.groupRequirementId).get();
            RequirementWeight requirementWeight = new RequirementWeight(request.weight,requirement,skill);
            this.repository.save(requirementWeight);            
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "grouprequirements/{requirementId}/weights/{id}", method = RequestMethod.DELETE)
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
