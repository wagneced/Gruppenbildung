package ch.zhaw.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.springboot.entities.GroupRequirement;

public interface GroupRequirementRepository extends JpaRepository<GroupRequirement, Long> {

}
