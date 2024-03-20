package com.domain.library.controller;

import com.domain.library.dto.BooksDTO;
import com.domain.library.entity.Books;
import com.domain.library.exception.CustomBadException;
import com.domain.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {
    BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BooksDTO> getAllPage(Pageable pageable){
        return bookService.pageableBooks(pageable);
    }
    @GetMapping("/with-borrowings")
    public List<Books> getAllWithBorrowings(Pageable pageable){
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Books getById(@PathVariable int id){
        Books book = bookService.findById(id);
        if(book == null) throw new CustomBadException("The book not found");
        return book;
    }

    @PostMapping
    public Books addBook(@RequestBody Books book){
        book.setId(0);
        return bookService.save(book);
    }
    @PutMapping
    public Books updateBook(@RequestBody Books book){
        return bookService.updateTheBook(book);
    }
    @DeleteMapping("/{id}")
    public Books deleteBook(@PathVariable int id){
        return bookService.deleteSingleBook(id);
    }
    @DeleteMapping("/delete-all/{id}")
    public Books deleteAllBook(@PathVariable int id){
        Books book = bookService.findById(id);
        return bookService.deleteAllBooks(book);
    }
    @GetMapping("/search")
    public List<Books> searchBooks(@RequestParam("query") String query){
        //System.out.println(java.time.LocalTime.now());
        List<Books> book = bookService.searchBook(query);
        //System.out.println(java.time.LocalTime.now());

        //46.551160900
        //46.800547500
        double exampleMatcherTimer = 46.800547500 - 46.551160900;
        //36.662992300
        //36.797608500
        double queryTimer = 36.797608500 - 36.662992300;
        System.out.println("ExampleMatcher--> " + exampleMatcherTimer);
        System.out.println("Query--> " + queryTimer);
        return book;
    }
    @GetMapping("/count")
    public Integer pageCount(){
        return bookService.pageCount();
    }
}
