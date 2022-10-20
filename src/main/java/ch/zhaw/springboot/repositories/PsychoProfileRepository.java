package ch.zhaw.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.springboot.entities.PsychoProfile;

public interface PsychoProfileRepository extends JpaRepository<PsychoProfile, Long> {

}