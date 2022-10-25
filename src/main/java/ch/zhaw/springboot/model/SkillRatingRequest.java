package ch.zhaw.springboot.model;

public class SkillRatingRequest {
    public long id;
    public int rating;
    
    public SkillRatingRequest(long id, int rating) {
        this.id = id;
        this.rating = rating;
    }
}
