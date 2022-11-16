package ch.zhaw.springboot.model;

import java.util.List;

public class PersonRequest {
    public long id;
    public String name;
    public String email;
    public long zhawId;
    public List<Long> courseIds;

    public PersonRequest(long id, String name, String email, long zhawId, List<Long> courseIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.zhawId = zhawId;
        this.courseIds = courseIds;
    }
}
