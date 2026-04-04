package com.kritika.controller;

import com.kritika.RestApiDemoApplication;
import com.kritika.dto.StudentRequestdto;
import com.kritika.entity.Student;
import com.kritika.exceptions.StudentNotFoundException;
import com.kritika.service.StudentService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRequestdto student){

        Student student1 = new Student();
        student1.setName(student.getName());
        student1.setEmail(student.getEmail());
        student1.setAge(student.getAge());

        Student s1 = service.createStudent(student1);
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

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentRequestdto studentRequestdto){
        Student s1 = new Student();
        s1.setName(studentRequestdto.getName());
        s1.setEmail(studentRequestdto.getEmail());
        s1.setAge(studentRequestdto.getAge());
        Student student = service.updateStudent(id,s1);
        return ResponseEntity.status(200).body(student);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> partialUpdate(@PathVariable Long id, @RequestBody StudentRequestdto studentRequestdto){
        Student S = service.partialUpdate(id,studentRequestdto);
        return ResponseEntity.status(200).body(S);
    }

    @GetMapping("/Search")
    public ResponseEntity<List<Student>> studentByAge(@RequestParam Integer age){
        return ResponseEntity.ok(service.getStudentByAge(age));
    }

    @GetMapping("/paged")
    public ResponseEntity<List<Student>> getStudentsPaged(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                service.getStudentPaged(page, size)
        );
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudentSorted(){
        return ResponseEntity.ok(service.sortStudentByAge());
    }
}
