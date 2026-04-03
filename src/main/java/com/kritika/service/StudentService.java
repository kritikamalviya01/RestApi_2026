package com.kritika.service;

import com.kritika.entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final List<Student> studentList = new ArrayList<>();

    public Student createStudent(Student student){
        studentList.add(student);
        return student;
    }

    public List<Student> getStudents(){
        return studentList;
    }

    public void deleteStudent(Long id){
        studentList.removeIf(Student -> Student.getId() == id);
        System.out.println("Done");
    }

    public Optional<Student> getStudentWithId(Long id){
       return studentList.stream().filter(Student -> Student.getId() == id)
               .findFirst();
    }
}
