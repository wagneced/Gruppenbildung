package ch.zhaw.springboot.restcontroller;

import java.util.ArrayList;
import java.util.Comparator;
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
import ch.zhaw.springboot.entities.GroupComposition;
import ch.zhaw.springboot.entities.GroupRequirement;
import ch.zhaw.springboot.entities.Person;
import ch.zhaw.springboot.entities.RequirementWeight;
import ch.zhaw.springboot.entities.SkillRating;
import ch.zhaw.springboot.model.TemporaryExtendedPersonObject;
import ch.zhaw.springboot.repositories.CourseRepository;
import ch.zhaw.springboot.repositories.GroupCompositionRepository;
import ch.zhaw.springboot.repositories.SkillRatingRepository;

@RestController
@CrossOrigin
public class GroupCompositionController {
    @Autowired
    private GroupCompositionRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SkillRatingRepository skillRatingRepository;

    @RequestMapping(value = "courses/{id}/groups", method = RequestMethod.GET)
    public ResponseEntity<List<GroupComposition>> findAllGroupsOfCourse(@PathVariable("id") long id) {
        List<GroupComposition> result = this.repository.findAllAssociatedGroupCompositionsByCourseId(id);
        return new ResponseEntity<List<GroupComposition>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "courses/{id}/groups", method = RequestMethod.POST)
    public ResponseEntity<List<GroupComposition>> generateGroups(@PathVariable("id") long id) {
        try {
            Course course = this.courseRepository.findById(id).get();
            List<GroupComposition> groups = new ArrayList<GroupComposition>();
            cleanCourse(course);
            List<Person> attendees = course.getAttendees();
            GroupRequirement groupRequirement = course.getGroupRequirement();
            int groupSize = groupRequirement.getGroupSize();
            int numberOfGroups = attendees.size() / groupSize;
            boolean generateEqualGroups = groupRequirement.getGenerateEqualGroups();

            if (course.getGroupCompositions() != null && !course.getGroupCompositions().isEmpty()) {
                course.cleanAllGroups();
                groups.addAll(course.getGroupCompositions());
            }

            List<TemporaryExtendedPersonObject> currentAttendeesWithScore = new ArrayList<TemporaryExtendedPersonObject>();

            for (Person attendee : attendees) {
                currentAttendeesWithScore.add(calculateScore(attendee, groupRequirement));
            }
            Comparator<TemporaryExtendedPersonObject> comparator = Comparator
                    .comparing(TemporaryExtendedPersonObject::getScore);

            currentAttendeesWithScore.sort(comparator);

            if (generateEqualGroups) {
                for (int i = 0; i < currentAttendeesWithScore.size(); i++) {
                    if ((i % numberOfGroups) >= groups.size()) {
                        // Index does not exist => Group has to be created (Just in first iteration)
                        // Important groups.size() is number of groups that currently exist
                        // If new groups have to be generated it reuses the sql objects
                        groups.add(this.repository.save(new GroupComposition(course)));
                    }
                    GroupComposition group = groups.get(i % numberOfGroups);
                    TemporaryExtendedPersonObject temp = currentAttendeesWithScore.get(i);
                    group.addMember(temp.getPerson());
                    group.addScore(temp.getScore());
                }
            } else {
                for (int i = 0; i < currentAttendeesWithScore.size(); i++) {
                    if ((i / groupSize) >= groups.size()) {
                        // Fills up first group before generating new one
                        // Important groups.size() is number of groups that currently exist
                        // If new groups have to be generated it reuses the sql objects
                        groups.add(this.repository.save(new GroupComposition(course)));
                    }
                    GroupComposition group = groups.get(i / groupSize);
                    TemporaryExtendedPersonObject temp = currentAttendeesWithScore.get(i);
                    group.addMember(temp.getPerson());
                    group.addScore(temp.getScore());
                }
            }

            List<GroupComposition> result = this.repository.saveAll(groups);
            courseRepository.save(course);
            return new ResponseEntity<List<GroupComposition>>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<GroupComposition>>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<List<GroupComposition>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private TemporaryExtendedPersonObject calculateScore(Person person, GroupRequirement requirement) {
        int totalScore = 0;
        for (RequirementWeight requirementWeight : requirement.getRequirementWeights()) {
            SkillRating skillRating = this.skillRatingRepository.findSkillRatingByPersonAndSkill(person,
                    requirementWeight.getSkill());
            if (skillRating != null) {
                totalScore += requirementWeight.getWeight() * skillRating.getRating();
            }
        }
        return new TemporaryExtendedPersonObject(person, totalScore);
    }

    private void cleanCourse(Course course) {
        for (GroupComposition group : course.getGroupCompositions()) {
            repository.delete(group);
        }
    }
}
