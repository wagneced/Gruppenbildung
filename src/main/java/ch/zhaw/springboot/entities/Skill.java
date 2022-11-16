package ch.zhaw.springboot.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RequirementWeight> requirementWeights;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SkillRating> skillRatingOfPersons;

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
        this.skillRatingOfPersons = new ArrayList<SkillRating>();
        this.requirementWeights = new ArrayList<RequirementWeight>();
    }

    public Skill() {

    }

    public long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    // Only used for preRemove in RequirementWeight class
    public void removeRequirementWeight(RequirementWeight requirementWeight) {
        this.requirementWeights.remove(requirementWeight);
    }

    // Only used for preRemove in SkillRating class
    public void removeSkillRating(SkillRating ratedSkill) {
        this.skillRatingOfPersons.remove(ratedSkill);
    }

}
