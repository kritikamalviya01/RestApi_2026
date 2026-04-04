package com.kritika.service;

import com.kritika.entity.Student;
import com.kritika.exceptions.StudentNotFoundException;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private Long currentId = 1L;

    private final List<Student> studentList = new ArrayList<>();

    public Student createStudent(Student student){
        student.setId(currentId++);
        studentList.add(student);
        return student;
    }

    public List<Student> getStudents(){
        return studentList;
    }

    public void deleteStudent(Long id){
        boolean removed = studentList.removeIf(Student -> Student.getId().equals(id));
        if(!removed){
            throw new StudentNotFoundException("Student not found with id "+ id);
        }
    }

    public Optional<Student> getStudentWithId(Long id){
       return studentList.stream().filter(Student -> Student.getId().equals(id))
               .findFirst();
    }

    public Student updateStudent(Long id, Student updatedStudent){
           Student existingStudent = getStudentWithId(id)
                   .orElseThrow(() ->
                           new StudentNotFoundException("Student not found with id "+id)
        );

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setAge(updatedStudent.getAge());

        return existingStudent;
    }
}
