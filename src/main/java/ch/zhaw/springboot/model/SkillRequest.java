package ch.zhaw.springboot.model;

public class SkillRequest {
    public long id;
    public String name;
    public String description;
    
    
    public SkillRequest(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
