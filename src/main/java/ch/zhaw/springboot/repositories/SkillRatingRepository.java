package ch.zhaw.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.zhaw.springboot.entities.Skill;
import ch.zhaw.springboot.entities.SkillRating;

public interface SkillRatingRepository extends JpaRepository<SkillRating, Long> {
    @Query("SELECT DISTINCT (weights.skill) FROM Person person"
            + " JOIN person.courses courses"
            + " JOIN courses.groupRequirement groupReq"
            + " JOIN groupReq.requirementWeights weights"
            + " JOIN person.skillRatings ratings"
            + " WHERE person.id = ?1 AND weights.skill NOT IN (ratings.skill)")
    public List<Skill> findSkillsRequiredToBeRatedByPerson(long id);
}
