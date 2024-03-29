package ch.zhaw.springboot.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

///
/// This is a helper-class to set the weight of each skill and group requirement seperatly
///
@Entity
public class RequirementWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "groupRequirement_id")
    private GroupRequirement groupRequirement;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public RequirementWeight(int weight, GroupRequirement groupRequirement, Skill skill) {
        this.weight = weight;
        this.groupRequirement = groupRequirement;
        this.skill = skill;
    }

    public RequirementWeight() {

    }

    public long getId() {
        return this.id;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @JsonIdentityReference(alwaysAsId = true)
    public GroupRequirement getGroupRequirement() {
        return this.groupRequirement;
    }

    public Skill getSkill() {
        return this.skill;
    }

    @PreRemove
    private void removeRequirementsWeightFromAssoziatedObjects() {
        skill.removeRequirementWeight(this);
        groupRequirement.removeRequirementWeight(this);
    }
}
