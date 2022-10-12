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
    private boolean generateEqualGroups;
    private int groupSize;
    private boolean orderByPsycho;
    
    @OneToMany(mappedBy = "groupRequirement", fetch = FetchType.LAZY)
    private List<Course> courses;
    
    @OneToMany(mappedBy = "groupRequirement", fetch = FetchType.LAZY)
    private List<RequirementWeight> requirementWeights;
    
    public GroupRequirement(boolean generateEqualGroups, int groupSize, boolean orderByPsycho) {
        this.generateEqualGroups = generateEqualGroups;
        this.groupSize = groupSize;
        this.orderByPsycho = orderByPsycho;
        this.requirementWeights = new ArrayList<RequirementWeight>();
        this.courses = new ArrayList<Course>();
    }
    
    public void setGenerateEqualGroups(boolean generateEqualGroups) {
        this.generateEqualGroups = generateEqualGroups;
    }
    
    public boolean getgenerateEqualGroups() {
        return this.generateEqualGroups;
    }
    
    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }
    
    public int getGroupSize() {
        return this.groupSize;
    }
    
    public void setOrderByPsycho(boolean orderByPsycho) {
        this.orderByPsycho = orderByPsycho;
    }
    
    public boolean getOrderByPsycho() {
        return this.orderByPsycho;
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
