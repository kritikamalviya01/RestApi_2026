package com.kritika.controller;

import com.kritika.dto.StudentRequestdto;
import com.kritika.entity.Student;
import com.kritika.exceptions.StudentNotFoundException;
import com.kritika.repository.StudentRepository;
import com.kritika.service.StudentServiceJPA;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/StudentJPA")
public class StudentControllerJPA {

    private final StudentServiceJPA studentServiceJPA;

    public StudentControllerJPA(StudentServiceJPA studentServiceJPA){
        this.studentServiceJPA = studentServiceJPA;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRequestdto student){

        Student student1 = new Student();
        student1.setName(student.getName());
        student1.setEmail(student.getEmail());
        student1.setAge(student.getAge());

        Student s1 = studentServiceJPA.createStudent(student1);
        return ResponseEntity.status(201).body(s1);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(){
        return ResponseEntity.ok(studentServiceJPA.getStudents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletStudent(@PathVariable Long id){
        studentServiceJPA.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentId(@PathVariable Long id){
        return studentServiceJPA.getStudentWithId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id "+ id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentRequestdto studentRequestdto){
        Student s1 = new Student();
        s1.setName(studentRequestdto.getName());
        s1.setEmail(studentRequestdto.getEmail());
        s1.setAge(studentRequestdto.getAge());
        Student student = studentServiceJPA.updateStudent(id,s1);
        return ResponseEntity.status(200).body(student);
    }
}
