package ch.zhaw.springboot.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private long zhawId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "psycho_profile_id")
    private PsychoProfile psychoProfile;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SkillRating> skillRatings;

    @ManyToMany(mappedBy = "attendees", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<Course> courses;

    @ManyToMany(mappedBy = "members", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<GroupComposition> groupCompositions;

    public Person(String name, String email, long zhawId) {
        this.name = name;
        this.email = email;
        this.zhawId = zhawId;
        // this.psychoProfile = new PsychoProfile(this);
        this.skillRatings = new ArrayList<SkillRating>();
        this.courses = new ArrayList<Course>();
        this.groupCompositions = new ArrayList<GroupComposition>();
    }

    public Person() {

    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getZhawId() {
        return this.zhawId;
    }

    public void setZhawId(long zhawId) {
        this.zhawId = zhawId;
    }

    public PsychoProfile getPsychoProfile() {
        return this.psychoProfile;
    }

    public void setPsychoProfile(PsychoProfile psychoProfile) {
        this.psychoProfile = psychoProfile;
    }

    public void addToGroupComposition(GroupComposition groupComposition) {
        this.groupCompositions.add(groupComposition);
    }

    @JsonIdentityReference(alwaysAsId = true)
    public List<GroupComposition> getGroupCompositions() {
        return this.groupCompositions;
    }

    public void addToCourse(Course course) {
        this.courses.add(course);
    }

    @JsonIdentityReference(alwaysAsId = true)
    public List<Course> getCourses() {
        return this.courses;
    }

    public List<SkillRating> getRatedSkills() {
        return this.skillRatings;
    }

    public void removeFromGroupComposition(GroupComposition groupComposition) {
        this.groupCompositions.remove(groupComposition);
    }

    public void removeFromCourse(Course course) {
        this.courses.remove(course);
    }

    // Only used for preRemove in SkillRating class
    public void removeSkillRating(SkillRating ratedSkill) {
        this.skillRatings.remove(ratedSkill);
    }
}
