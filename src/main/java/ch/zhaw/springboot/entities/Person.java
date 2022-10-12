package ch.zhaw.springboot.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private long zhawId;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SkillRating> skillRatings;
    
    @ManyToMany(mappedBy = "attendees")
    private List<Course> courses;
    
    @ManyToMany(mappedBy = "members")
    private List<Group> groups;
    
    public Person (String name, String email, long zhawId) {
        this.name = name;
        this.email = email;
        this.zhawId = zhawId;
        this.skillRatings = new ArrayList<SkillRating>();
        this.courses = new ArrayList<Course>();
        this.groups = new ArrayList<Group>();
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
    
    public void addToGroup(Group group) {
        this.groups.add(group);
    }
    
    public List<Group> getGroup() {
        return this.groups;
    }
    
    public void addToCourse(Course course) {
        this.courses.add(course);
    }
    
    public List<Course> getCourses() {
        return this.courses;
    }
    
    public List<SkillRating> getRatedSkills() {
        return this.skillRatings;
    }
    
    public void removeFromGroup(Group group) {
        this.groups.remove(group);
    }
    
    public void removeFromCourse(Course course) {
        this.courses.remove(course);
    }
    
    // Only used for preRemove in SkillRating class
    public void removeSkillRating(SkillRating ratedSkill) {
        this.skillRatings.remove(ratedSkill);
    }
}
