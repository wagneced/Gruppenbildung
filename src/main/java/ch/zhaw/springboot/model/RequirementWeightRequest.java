package ch.zhaw.springboot.model;

public class RequirementWeightRequest {
    public long id;
    public int weight;
    public long groupRequirementId;
    public long skillId;

    public RequirementWeightRequest(long id, int weight, long groupRequirementId, long skillId) {
        this.id = id;
        this.weight = weight;
        this.groupRequirementId = groupRequirementId;
        this.skillId = skillId;
    }

}
