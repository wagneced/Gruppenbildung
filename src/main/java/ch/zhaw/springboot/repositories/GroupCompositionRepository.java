package ch.zhaw.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.zhaw.springboot.entities.GroupComposition;

public interface GroupCompositionRepository extends JpaRepository<GroupComposition, Long> {
    @Query("SELECT g FROM GroupComposition g JOIN g.course c WHERE c.id = ?1")
    public List<GroupComposition> findAllAssociatedGroupCompositionsByCourseId(long courseId);
}
