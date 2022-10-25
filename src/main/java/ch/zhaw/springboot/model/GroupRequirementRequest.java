package ch.zhaw.springboot.model;

public class GroupRequirementRequest {
    public long id;
    public String name;
    public boolean generateEqualGroups;
    public int groupSize;
    public boolean groupByPersonality;
    public long courseId;
    
    public GroupRequirementRequest(long id, String name, boolean generateEqualGroups, int groupSize, boolean groupByPersonality, long courseId) {
        this.id = id;
        this.name = name;
        this.generateEqualGroups = generateEqualGroups;
        this.groupSize = groupSize;
        this.groupByPersonality = groupByPersonality;
        this.courseId = courseId;
    }
}
