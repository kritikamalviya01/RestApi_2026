package com.kritika.repository;

import com.kritika.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    List<Student> findByNameContaining(String name);

    List<Student> findByAge(Integer age);

    List<Student> findByAgeGreaterThan(Integer age);
}
