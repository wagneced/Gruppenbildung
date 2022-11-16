package ch.zhaw.springboot.model;

import ch.zhaw.springboot.entities.Skill;

public class SkillRatingRequest {
    public long id;
    public int rating;
    public Skill skill;

    public SkillRatingRequest(long id, int rating, Skill skill) {
        this.id = id;
        this.rating = rating;
        this.skill = skill;
    }
}
