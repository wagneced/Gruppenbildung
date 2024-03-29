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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

///
/// This is a helper-class to allow each user to set their skills separate  
///
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SkillRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public SkillRating(int rating, Person person, Skill skill) {
        this.rating = rating;
        this.person = person;
        this.skill = skill;
    }

    public SkillRating() {

    }

    public long getId() {
        return this.id;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @JsonIgnore
    public Person getPerson() {
        return this.person;
    }

    public Skill getSkill() {
        return this.skill;
    }

    @PreRemove
    private void removeSkillRatingFromAssoziatedObjects() {
        skill.removeSkillRating(this);
        person.removeSkillRating(this);
    }
}
