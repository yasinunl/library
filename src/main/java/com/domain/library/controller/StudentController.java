package com.domain.library.controller;

import com.domain.library.entity.Students;
import com.domain.library.exception.CustomBadException;
import com.domain.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Students> getAllStudent(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Students getStudentById(@PathVariable int id){
        Students student = studentService.getStudentById(id);
        if(student == null) throw new CustomBadException("The student not found");
        return student;
    }

    @PostMapping
    public Students addStudent(@RequestBody Students student){
        student.setId(0);
        return studentService.addStudent(student);
    }
    @PutMapping
    public Students updateStudent(@RequestBody Students student){
        return studentService.updateTheStudent(student);
    }
    @DeleteMapping("/{id}")
    public Students deleteStudent(@PathVariable int id){
        return studentService.deleteStudent(id);
    }
    @GetMapping("/search")
    public List<Students> searchStudent(@RequestParam("query") String query){
        return studentService.searchStudent(query);
    }
}
