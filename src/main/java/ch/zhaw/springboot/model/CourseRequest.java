package ch.zhaw.springboot.model;

public class CourseRequest {
    public long id;
    public String name;
    public boolean courseActive;
    public long groupRequirementId;

    public CourseRequest(long id, String name, boolean courseActive, long groupRequirementId) {
        this.id = id;
        this.name = name;
        this.courseActive = courseActive;
        this.groupRequirementId = groupRequirementId;
    }
}
