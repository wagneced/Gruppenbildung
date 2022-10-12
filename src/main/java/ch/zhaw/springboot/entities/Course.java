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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private boolean courseActive;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private GroupRequirement groupRequirement;
    
    @ManyToMany
    @JoinTable(name = "course_attendees", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> attendees;
    
    public Course(String name) {
        this.name = name;
        this.courseActive = true;
        this.attendees = new ArrayList<Person>();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setCourseState(boolean courseActive) {
        this.courseActive = courseActive;
    }
    
    public boolean getCourseState() {
        return this.courseActive;
    }
    
    public List<Person> getAttendees() {
        return this.attendees;
    }
    
    public void addAttendee(Person attendee) {
        this.attendees.add(attendee);
    }
    
    public void removeAttendee(Person attendee) {
        this.attendees.remove(attendee);
    }
    
}
