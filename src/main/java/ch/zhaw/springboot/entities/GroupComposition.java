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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GroupComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private int score;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "group_composition_members", joinColumns = @JoinColumn(name = "group_cPsomposition_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> members;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Course course;
    
    public GroupComposition(Course course) {
        this.course = course;
        this.members = new ArrayList<Person>();
    }
    
    public GroupComposition(Course course, List<Person> members) {
        this.course = course;
        this.members = members;
    }
    
    public GroupComposition() {
        
    }
    
    public long getId() {
        return this.id;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public void addScore(int score) {
        this.score += score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    @JsonIdentityReference(alwaysAsId = true)
    public Course getCourse() {
        return this.course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public void addMember(Person member) {
        this.members.add(member);
    }
    
    public List<Person> getMembers() {
        return this.members;
    }
    
    public void removeMember(Person member) {
        this.members.remove(member);
    }
    
}
