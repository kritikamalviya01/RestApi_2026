package com.kritika.repository;

import com.kritika.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    List<Student> findByNameContaining(String name);

    List<Student> findByAge(Integer age);

    List<Student> findByAgeGreaterThan(Integer age);

    @Query("SELECT s FROM Student s WHERE s.age > :age")
    List<Student> getStudentsAboveAge(@Param("age") Integer age);

}
