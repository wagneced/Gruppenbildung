package ch.zhaw.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.zhaw.springboot.entities.Person;
import ch.zhaw.springboot.entities.Skill;
import ch.zhaw.springboot.entities.SkillRating;

public interface SkillRatingRepository extends JpaRepository<SkillRating, Long> {
    @Query("SELECT DISTINCT (weigths.skill) FROM Person person"
            + " JOIN person.courses c"
            + " JOIN c.groupRequirement g"
            + " JOIN g.requirementWeights weigths"
            + " WHERE person.id = ?1")
    public List<Skill> findSkillsRequiredToBeRatedByPerson(long id);
    
    @Query("SELECT skillRating FROM SkillRating skillRating WHERE skillRating.person = ?1 AND skillRating.skill = ?2")
    public SkillRating findSkillRatingByPersonAndSkill(Person person, Skill skill);
}
