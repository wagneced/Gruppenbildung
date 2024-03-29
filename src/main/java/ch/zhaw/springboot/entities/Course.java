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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private boolean courseActive;

    @ManyToOne(fetch = FetchType.LAZY)
    private GroupRequirement groupRequirement;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupComposition> groupCompositions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(name = "course_attendees", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> attendees;

    public Course(String name) {
        this.name = name;
        this.courseActive = true;
        this.attendees = new ArrayList<Person>();
        this.groupCompositions = new ArrayList<GroupComposition>();
    }

    public Course(String name, boolean courseActive) {
        this.name = name;
        this.courseActive = courseActive;
        this.attendees = new ArrayList<Person>();
        this.groupCompositions = new ArrayList<GroupComposition>();
    }

    public Course() {

    }

    public long getId() {
        return this.id;
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

    public void setGroupRequirement(GroupRequirement groupRequirement) {
        this.groupRequirement = groupRequirement;
    }

    @JsonIdentityReference(alwaysAsId = true)
    public GroupRequirement getGroupRequirement() {
        return this.groupRequirement;
    }

    @JsonIgnore
    public List<Person> getAttendees() {
        return this.attendees;
    }

    public void addAttendee(Person attendee) {
        if (!this.attendees.contains(attendee)) {
            this.attendees.add(attendee);
        }
    }

    public void removeAttendee(Person attendee) {
        if (this.attendees.contains(attendee)) {
            this.attendees.remove(attendee);
        }
    }

    public List<GroupComposition> getGroupCompositions() {
        return this.groupCompositions;
    }

    public void addGroupComposition(GroupComposition groupComposition) {
        if (!this.groupCompositions.contains(groupComposition)) {
            this.groupCompositions.add(groupComposition);
        }
    }

    public void removeGroup(GroupComposition groupComposition) {
        if (this.groupCompositions.contains(groupComposition)) {
            this.groupCompositions.remove(groupComposition);
        }
    }

    public void cleanAllGroups() {
        for (GroupComposition group : this.groupCompositions) {
            group.removeAllMembers();
            group.setScore(0);
        }
    }

}
