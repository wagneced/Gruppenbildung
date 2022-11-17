package ch.zhaw.springboot.model;

import ch.zhaw.springboot.entities.Skill;

public class RequirementWeightRequest {
    public long id;
    public int weight;
    public long groupRequirementId;
    public Skill skill;

    public RequirementWeightRequest(long id, int weight, long groupRequirementId, Skill skill) {
        this.id = id;
        this.weight = weight;
        this.groupRequirementId = groupRequirementId;
        this.skill = skill;
    }

}
