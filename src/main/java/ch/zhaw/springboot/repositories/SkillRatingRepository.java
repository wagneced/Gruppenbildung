package ch.zhaw.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.springboot.entities.SkillRating;

public interface SkillRatingRepository extends JpaRepository<SkillRating, Long> {

}
