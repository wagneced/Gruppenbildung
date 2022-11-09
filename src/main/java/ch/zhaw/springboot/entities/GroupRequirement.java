package ch.zhaw.springboot.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class GroupRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private boolean generateEqualGroups;
    private int groupSize;
    private boolean groupByPersonality;
    
    @OneToMany(mappedBy = "groupRequirement", fetch = FetchType.LAZY)
    private List<Course> courses;
    
    @OneToMany(mappedBy = "groupRequirement", fetch = FetchType.LAZY)
    private List<RequirementWeight> requirementWeights;
    
    public GroupRequirement(String name, boolean generateEqualGroups, int groupSize, boolean groupByPersonality) {
        this.name = name;
        this.generateEqualGroups = generateEqualGroups;
        this.groupSize = groupSize;
        this.groupByPersonality = groupByPersonality;
        this.requirementWeights = new ArrayList<RequirementWeight>();
        this.courses = new ArrayList<Course>();
    }
    
    public GroupRequirement() {
        
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
    
    public void setGenerateEqualGroups(boolean generateEqualGroups) {
        this.generateEqualGroups = generateEqualGroups;
    }
    
    public boolean getGenerateEqualGroups() {
        return this.generateEqualGroups;
    }
    
    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }
    
    public int getGroupSize() {
        return this.groupSize;
    }
    
    public void setGroupByPersonality(boolean groupByPersonality) {
        this.groupByPersonality = groupByPersonality;
    }
    
    public boolean getGroupByPersonality() {
        return this.groupByPersonality;
    }
    
    public List<RequirementWeight> getRequirementWeights() {
        return this.requirementWeights;
    }
    
    public List<Course> getCourses() {
        return this.courses;
    }
    
    public void addCourse(Course course) {
        this.courses.add(course);
    }
    
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }
    
    // Only used for preRemove in RequirementWeight class
    public void removeRequirementWeight(RequirementWeight requirementWeight) {
        this.requirementWeights.remove(requirementWeight);
    }
}
