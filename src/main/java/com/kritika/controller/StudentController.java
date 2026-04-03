package com.kritika.controller;

import com.kritika.RestApiDemoApplication;
import com.kritika.entity.Student;
import com.kritika.exceptions.StudentNotFoundException;
import com.kritika.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student s1 = service.createStudent(student);
        return ResponseEntity.status(201).body(s1);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(){
       return ResponseEntity.ok(service.getStudents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletStudent(@PathVariable Long id){
        service.deleteStudent(id);
         return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentId(@PathVariable Long id){
        return service.getStudentWithId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id "+ id));
    }

}
