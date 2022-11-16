package ch.zhaw.springboot.model;

public class SkillRatingRequest {
    public long id;
    public int rating;
    public long skillId;

    public SkillRatingRequest(long id, int rating, long skillId) {
        this.id = id;
        this.rating = rating;
        this.skillId = skillId;
    }
}
