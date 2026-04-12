package com.kritika.controller;

import com.kritika.dto.StudentRequestdto;
import com.kritika.entity.Address;
import com.kritika.entity.Courses;
import com.kritika.entity.Student;
import com.kritika.exceptions.StudentNotFoundException;
import com.kritika.service.StudentServiceJPA;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/StudentJPA")
public class StudentControllerJPA {

    private final StudentServiceJPA studentServiceJPA;

    public StudentControllerJPA(StudentServiceJPA studentServiceJPA) {
        this.studentServiceJPA = studentServiceJPA;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRequestdto student) {

        Student student1 = new Student();
        student1.setName(student.getName());
        student1.setEmail(student.getEmail());
        student1.setAge(student.getAge());

        if(student.getAddresses() != null){
            for(Address address: student.getAddresses()){
                address.setStudent(student1);
            }
            student1.setAddress(student.getAddresses());
        }

        if(student.getCourses() != null){
            for(Courses courses: student.getCourses()){
                courses.getStudent().add(student1);
            }
            student1.setCourses(student.getCourses());
        }

        Student s1 = studentServiceJPA.createStudent(student1);

        return ResponseEntity.status(201).body(s1);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentServiceJPA.getStudents());
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<Address> setAddress(@PathVariable Long id, @RequestBody Address address){
        return ResponseEntity.ok(studentServiceJPA.updateAddress(id, address));
    }

    @PostMapping("/courses")
    public ResponseEntity<Courses> createCourse(@RequestBody Courses courses){
        return ResponseEntity.ok(studentServiceJPA.createCourse(courses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletStudent(@PathVariable Long id) {
        studentServiceJPA.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentId(@PathVariable Long id) {
        return studentServiceJPA.getStudentWithId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentRequestdto studentRequestdto) {
        Student s1 = new Student();
        s1.setName(studentRequestdto.getName());
        s1.setEmail(studentRequestdto.getEmail());
        s1.setAge(studentRequestdto.getAge());
        Student student = studentServiceJPA.updateStudent(id, s1);
        return ResponseEntity.status(200).body(student);
    }

    @GetMapping("/search-name")
    public ResponseEntity<List<Student>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(studentServiceJPA.searchByName(name));
    }

    @GetMapping("/search-age")
    public ResponseEntity<List<Student>> searchByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(studentServiceJPA.searchByAge(age));
    }

    @GetMapping("/olderthan")
    public ResponseEntity<List<Student>> getGreaterThanAge(@RequestParam Integer age){
        return ResponseEntity.ok(studentServiceJPA.getGreaterThan(age));
    }

    @GetMapping("/search-email")
    public ResponseEntity<Student> searchByEmail(@RequestParam String email) {
        return ResponseEntity.ok(studentServiceJPA.searchByEmail(email).orElseThrow(() -> new StudentNotFoundException("EMail not found")));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Student>> getStudentsPaged(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                studentServiceJPA.getStudentPage(page, size)
        );
    }

    @GetMapping("/aboveAge")
    public ResponseEntity<List<Student>> getStudentAboveAge( @RequestParam Integer  age){
        return ResponseEntity.ok(studentServiceJPA.getStudentAboveAge(age));
    }
}
