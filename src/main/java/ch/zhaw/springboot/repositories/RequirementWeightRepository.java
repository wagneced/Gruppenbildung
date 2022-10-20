package ch.zhaw.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.springboot.entities.RequirementWeight;

public interface RequirementWeightRepository extends JpaRepository<RequirementWeight, Long> {

}
