package ch.zhaw.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.zhaw.springboot.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT group FROM Group group JOIN group.course course WHERE course.id = ?1")
    public List<Group> findAllGroupsOfCourse(long courseId);
}
