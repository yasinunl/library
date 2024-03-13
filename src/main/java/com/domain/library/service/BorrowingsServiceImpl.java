package com.domain.library.service;

import com.domain.library.dto.UserDTO;
import com.domain.library.entity.Books;
import com.domain.library.entity.Borrowings;
import com.domain.library.entity.Students;
import com.domain.library.exception.CustomBadException;
import com.domain.library.exception.CustomSuccessException;
import com.domain.library.repository.BookRepository;
import com.domain.library.repository.BorrowingsRepository;
import com.domain.library.repository.StudentRepository;
import com.domain.library.repository.UserDTORepo;
import com.domain.library.security.entity.User;
import com.domain.library.security.repo.UserRepo;
import com.domain.library.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingsServiceImpl implements BorrowingsService{
    BorrowingsRepository borrowingsRepository;
    BookRepository bookRepository;
    StudentRepository studentRepository;
    JwtService jwtService;
    UserDTORepo userRepo;

    @Autowired
    public BorrowingsServiceImpl(BorrowingsRepository borrowingsRepository, BookRepository bookRepository,
                                 StudentRepository studentRepository, JwtService jwtService, UserDTORepo userRepo) {
        this.borrowingsRepository = borrowingsRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }



    @Override
    public List<Borrowings> getAll() {
        return borrowingsRepository.findAll();
    }

    @Override
    public Borrowings addBorrowing(Borrowings borrowing, HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String email = jwtService.extractEmail(token);
        Optional<UserDTO> theUser =  userRepo.findByEmail(email);
        UserDTO user;
        if (theUser.isPresent()) user = theUser.get(); else throw new RuntimeException("The user is not found");
        Optional<Books> theBook = bookRepository.findById(borrowing.getBooks().getId());
        Books book;
        if(theBook.isPresent()){
            book = theBook.get();
            if(book.getAvailableQuantity() <= 0) throw new RuntimeException("The book is not available");
            book.setAvailableQuantity(bookRepository.decreaseAvailableQuantity(book.getId()));
            borrowing.setBooks(book);
        }
        else throw new RuntimeException("The book not found");
        Optional<Students> theStudent = studentRepository.findById(borrowing.getStudent().getId());
        Students student;
        if(theStudent.isPresent()){
            student = theStudent.get();
            student.setBorrowedBooks(student.getBorrowedBooks() + 1);
            borrowing.setStudent(student);
        }else throw new CustomBadException("The student not fount");
        Date returnDate = new Date();
        borrowing.setBorrowDate(new Date());
        if(borrowing.getReturnDate() == null){
            returnDate.setMonth(returnDate.getMonth() + 2);
            borrowing.setReturnDate(returnDate);
        }
        borrowing.setUser(user);
        System.out.println(borrowing);
        return borrowingsRepository.save(borrowing);
    }

    @Override
    public Borrowings deleteBorrowing(int id) {
        Optional<Borrowings> optional = borrowingsRepository.findById(id);
        Borrowings borrowing;
        if(optional.isPresent())  borrowing = optional.get();
        else throw new CustomBadException("The borrowing not found");
        Borrowings borrow = new Borrowings();
        borrow.setStudent(borrowing.getStudent());
        borrow.setBooks(borrowing.getBooks());
        borrow.setBorrowDate(new Date());
        Books book = borrowing.getBooks();
        Students student = borrowing.getStudent();
        borrowing.setStudent(null);
        borrowing.setBooks(null);
        book.setAvailableQuantity(bookRepository.increaseAvailableQuantity(book.getId()));
        student.setBorrowedBooks(student.getBorrowedBooks() - 1);
        bookRepository.save(book);
        studentRepository.save(student);
        borrowingsRepository.delete(borrowing);
        throw new CustomSuccessException("Success");
    }

    @Override
    public Borrowings getById(int id) {
        Optional<Borrowings> optional = borrowingsRepository.findById(id);
        Borrowings borrowing;
        if(optional.isPresent())  borrowing = optional.get();
        else throw new CustomBadException("The borrowing not found");
        return borrowing;
    }

    @Override
    public Borrowings updateBorrowing(Borrowings borrowing) {
        if(borrowing.getId() == 0) throw new CustomBadException("Please write an id");
        Optional<Borrowings> optional = borrowingsRepository.findById(borrowing.getId());
        Borrowings theBorrowing;
        if(optional.isPresent()) theBorrowing = optional.get();
        else throw new RuntimeException("The borrowing not found");
        if(borrowing.getBorrowDate() != null)
            theBorrowing.setBorrowDate(borrowing.getBorrowDate());
        if(borrowing.getReturnDate() != null)
            theBorrowing.setReturnDate(borrowing.getReturnDate());
        if(borrowing.getStudent() != null){
            Optional<Students> optionalS = studentRepository.findById(borrowing.getStudent().getId());
            Students student;
            if(optionalS.isPresent())
                student = optionalS.get();
            else
                throw new CustomBadException("There is no student");
            if(theBorrowing.getStudent().getId() != student.getId()) {
                theBorrowing.getStudent().setBorrowedBooks(theBorrowing.getStudent().getBorrowedBooks() - 1);
                studentRepository.save(theBorrowing.getStudent());
                student.setBorrowedBooks(student.getBorrowedBooks() + 1);
            }
            theBorrowing.setStudent(student);
        }
        if(borrowing.getBooks() != null){
            Optional<Books> optionalB = bookRepository.findById(borrowing.getBooks().getId());
            Books book;
            if(optionalB.isPresent())
                book = optionalB.get();
            else
                throw new CustomBadException("There is no student");
            if(theBorrowing.getBooks().getId() != book.getId()) {
                theBorrowing.getBooks().setAvailableQuantity(theBorrowing.getBooks().getAvailableQuantity() + 1);
                bookRepository.save(theBorrowing.getBooks());
                book.setAvailableQuantity(book.getAvailableQuantity() - 1);
            }
            theBorrowing.setBooks(book);
        }
        return borrowingsRepository.save(theBorrowing);
    }
}
