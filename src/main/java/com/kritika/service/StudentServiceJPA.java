package com.kritika.service;

import com.kritika.entity.Student;
import com.kritika.exceptions.StudentNotFoundException;
import com.kritika.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentServiceJPA {

    private final StudentRepository studentRepository;

    public StudentServiceJPA(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void deleteStudent(Long id){
         studentRepository.deleteById(id);
    }

    public Optional<Student> getStudentWithId(Long id){
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student student){
            Student existingStudent = getStudentWithId(id).orElseThrow(() -> new StudentNotFoundException("Student Not Found"));

            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setAge(student.getAge());

           return studentRepository.save(existingStudent);
    }


}
