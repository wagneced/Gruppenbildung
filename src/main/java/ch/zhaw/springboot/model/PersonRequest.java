package ch.zhaw.springboot.model;

public class PersonRequest {
    public long id;
    public String name;
    public String email;
    public long zhawId;
    public long courseId;
    
    public PersonRequest(long id, String name, String email, long zhawId, long courseId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.zhawId = zhawId;
        this.courseId = courseId;
    }
}
