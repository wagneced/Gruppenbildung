package ch.zhaw.springboot.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToMany
    @JoinTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> members;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    
    public Group(Course course) {
        this.course = course;
        this.members = new ArrayList<Person>();
    }
    
    public Group(Course course, List<Person> members) {
        this.course = course;
        this.members = members;
    }
    
    public long getId() {
        return this.id;
    }
    
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
