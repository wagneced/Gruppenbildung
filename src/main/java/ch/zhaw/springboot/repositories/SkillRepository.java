package ch.zhaw.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.zhaw.springboot.entities.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT DISTINCT (weights.skill) FROM GroupRequirement requirement"
            + " JOIN requirement.requirementWeights weights"
            + " JOIN weights.skill"
            + " WHERE requirement.id = ?1")
    public List<Skill> findSkillsUsedByGroupRequirement(long id);
}
