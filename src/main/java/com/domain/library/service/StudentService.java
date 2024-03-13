package com.domain.library.service;

import com.domain.library.entity.Students;
import com.domain.library.repository.StudentRepository;

import java.util.List;

public interface StudentService{
    List<Students> getAllStudents();
    Students getStudentById(int id);
    Students addStudent(Students student);
    Students deleteStudent(int id);
    Students updateTheStudent(Students student);
    List<Students> searchStudent(String query);

}
