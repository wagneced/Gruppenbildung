package ch.zhaw.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.springboot.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
