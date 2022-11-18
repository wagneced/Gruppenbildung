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

import ch.zhaw.springboot.entities.GroupRequirement;
import ch.zhaw.springboot.entities.RequirementWeight;
import ch.zhaw.springboot.entities.Skill;
import ch.zhaw.springboot.model.GroupRequirementRequest;
import ch.zhaw.springboot.model.RequirementWeightRequest;
import ch.zhaw.springboot.repositories.GroupRequirementRepository;
import ch.zhaw.springboot.repositories.RequirementWeightRepository;
import ch.zhaw.springboot.repositories.SkillRepository;

@RestController
@CrossOrigin
public class GroupRequirementRestController {
    @Autowired
    private GroupRequirementRepository repository;

    @Autowired
    private RequirementWeightRepository weightRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping(value = "grouprequirements", method = RequestMethod.GET)
    public ResponseEntity<List<GroupRequirement>> getAllGroupRequirements() {
        List<GroupRequirement> result = this.repository.findAll();
        return new ResponseEntity<List<GroupRequirement>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "grouprequirements/{id}", method = RequestMethod.GET)
    public ResponseEntity<GroupRequirement> getGroupRequirement(@PathVariable("id") long id) {
        Optional<GroupRequirement> result = this.repository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<GroupRequirement>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<GroupRequirement>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "grouprequirements", method = RequestMethod.POST)
    public ResponseEntity<Long> createGroupRequirement(@RequestBody GroupRequirementRequest request) {
        GroupRequirement groupReq = new GroupRequirement(request.name, request.generateEqualGroups, request.groupSize,
                request.groupByPersonality);
        groupReq = this.repository.save(groupReq);

        updateOrCreateRequirementWeight(request.weightRequests, groupReq);

        GroupRequirement result = this.repository.save(groupReq);
        return new ResponseEntity<Long>(result.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "grouprequirements/", method = RequestMethod.PUT)
    public ResponseEntity<Long> updateGroupRequirement(@RequestBody GroupRequirementRequest request) {
        try {
            GroupRequirement groupReq = this.repository.findById(request.id).get();
            groupReq.setName(request.name);
            groupReq.setGenerateEqualGroups(request.generateEqualGroups);
            groupReq.setGroupSize(request.groupSize);
            groupReq.setGroupByPersonality(request.groupByPersonality);

            updateOrCreateRequirementWeight(request.weightRequests, groupReq);

            GroupRequirement result = this.repository.save(groupReq);

            return new ResponseEntity<Long>(result.getId(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "grouprequirements/{requirementId}/weights/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRequirementWeight(@PathVariable("id") long id) {
        try {
            this.weightRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateOrCreateRequirementWeight(List<RequirementWeightRequest> weightRequests,
            GroupRequirement groupReq) {
        RequirementWeight temporaryObject;
        Optional<RequirementWeight> weight;
        Optional<Skill> skill;
        for (RequirementWeightRequest weightRequest : weightRequests) {
            if (weightRequest.id > 0) {
                weight = weightRepository.findById(weightRequest.id);
                if (weight.isPresent()) {
                    temporaryObject = weight.get();
                    temporaryObject.setWeight(weightRequest.weight);
                    weightRepository.save(temporaryObject);
                }
            } else {
                skill = skillRepository.findById(weightRequest.skill.getId());
                if (skill.isPresent()) {
                    temporaryObject = new RequirementWeight(weightRequest.weight, groupReq, skill.get());
                    weightRepository.save(temporaryObject);
                }
            }
        }
    }
}
