package ch.zhaw.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.springboot.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
