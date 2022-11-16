package ch.zhaw.springboot.model;

import java.util.List;

public class GroupRequirementRequest {
    public long id;
    public String name;
    public boolean generateEqualGroups;
    public int groupSize;
    public boolean groupByPersonality;
    public List<Long> courseIds;
    public List<RequirementWeightRequest> weightRequests;

    public GroupRequirementRequest(long id, String name, boolean generateEqualGroups, int groupSize,
            boolean groupByPersonality, List<Long> courseIds, List<RequirementWeightRequest> weightRequests) {
        this.id = id;
        this.name = name;
        this.generateEqualGroups = generateEqualGroups;
        this.groupSize = groupSize;
        this.groupByPersonality = groupByPersonality;
        this.courseIds = courseIds;
        this.weightRequests = weightRequests;
    }
}
