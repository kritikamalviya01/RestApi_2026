package com.kritika.repository;

import com.kritika.entity.Courses;
import com.kritika.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses, Long> {
}
