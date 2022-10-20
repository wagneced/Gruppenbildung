package ch.zhaw.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.springboot.entities.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
