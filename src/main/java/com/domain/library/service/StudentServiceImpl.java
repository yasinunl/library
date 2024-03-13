package com.domain.library.service;

import com.domain.library.entity.Students;
import com.domain.library.exception.CustomBadException;
import com.domain.library.exception.CustomSuccessException;
import com.domain.library.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{
    StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Students> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Students getStudentById(int id) {
        Optional<Students> optional = studentRepository.findById(id);
        Students student;
        if(optional.isPresent()) student = optional.get();
        else throw new CustomBadException("The student not found");
        return student;
    }

    @Override
    public Students addStudent(Students student) {
        return studentRepository.save(student);
    }

    @Override
    public Students deleteStudent(int id) {
        Optional<Students> optional = studentRepository.findById(id);
        Students student;
        if(optional.isPresent()) {
            student = optional.get();
            studentRepository.delete(student);
            throw new CustomSuccessException("Success");
        }
        throw new CustomBadException("The student not found");
    }

    @Override
    public Students updateTheStudent(Students student) {
        Optional<Students> optional = studentRepository.findById(student.getId());
        Students theStudent;
        if (optional.isPresent()) {
            theStudent = optional.get();
            if (student.getFirstName() != null)
                theStudent.setFirstName(student.getFirstName());
            if (student.getLastName() != null)
                theStudent.setLastName(student.getLastName());
            if (student.getEmail() != null)
                theStudent.setEmail(student.getEmail());

            return studentRepository.save(theStudent);
        }
        throw new CustomBadException("Id not found");
    }

    @Override
    public List<Students> searchStudent(String query) {
       /* ExampleMatcher matcher = ExampleMatcher
                .matchingAny()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        String[] queryTerms = query.split(" "); // Split query into individual terms

        List<Students> resultBooks = new ArrayList<>();

        for (String term : queryTerms) {
            Students student = new Students();
            student.setFirstName(term);
            student.setLastName(term);
            student.setEmail(term);
            student.setBorrowedBooks(-1);

            Example<Students> example = Example.of(student, matcher);

            List<Students> termResults = studentRepository.findAll(example);
            resultBooks.addAll(termResults);
        }
*/
        // Remove duplicates from the result
        //return resultBooks.stream().distinct().collect(Collectors.toList());

        // Query Searching
        return studentRepository.searchStudent(query);
    }
}
