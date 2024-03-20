package com.domain.library.service;

import com.domain.library.dto.BooksDTO;
import com.domain.library.entity.Books;
import com.domain.library.exception.CustomBadException;
import com.domain.library.exception.CustomSuccessException;
import com.domain.library.repository.BookPageableRepository;
import com.domain.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final BookPageableRepository bookPageableRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookPageableRepository bookPageableRepository) {
        this.bookRepository = bookRepository;
        this.bookPageableRepository = bookPageableRepository;
    }

    @Override
    public List<Books> findAll(Pageable pageable) {
        if(pageable.getPageSize() == 20)
            pageable = PageRequest.of(pageable.getPageNumber(), 1000);
        return bookRepository.findAll(pageable).toList();
    }
    @Override
    public List<Books> findOnlyBooks() {
        List<Books> books = bookRepository.findAll();
        books.forEach(book -> book.setBorrowings(null)); // Set books to null to avoid serialization
        return books;
    }

    @Override
    public List<BooksDTO> pageableBooks(Pageable pageable) {
        if(pageable.getPageSize() == 20)
            pageable = PageRequest.of(pageable.getPageNumber(), 2);
        return bookPageableRepository.findAll(pageable).toList();
    }

    @Override
    public Integer pageCount() {
        return bookRepository.countBooks() - 1;
    }


    @Override
    public Books findById(int id) {
        Optional<Books> optional = bookRepository.findById(id);
        Books book;
        if(optional.isPresent()) book = optional.get();
        else throw new CustomBadException("The book not found");
        return book;
    }

    @Override
    public Books save(Books book) {
        return bookRepository.addNewBook(book);
    }

    @Override
    public Books deleteSingleBook(int id) {
        bookRepository.deleteSingleBook(id);
        throw new CustomSuccessException("Success");
    }

    @Override
    public Books deleteAllBooks(Books book) {
        book.setTotalQuantity(0);
        book.setAvailableQuantity(0);
        bookRepository.delete(book);
        throw new CustomSuccessException("Success");
    }

    @Override
    public Books updateTheBook(Books book) {
        Optional<Books> optional = bookRepository.findById(book.getId());
        Books theBook;
        if(optional.isPresent()){
            theBook = optional.get();
        if(book.getBookTitle() != null)
            theBook.setBookTitle(book.getBookTitle());
        if(book.getBookAuthor() != null)
            theBook.setBookAuthor(book.getBookAuthor());
        if(book.getYearPublication() != null)
            theBook.setYearPublication(book.getYearPublication());
        if(book.getPublisher() != null)
            theBook.setPublisher(book.getPublisher());
        if(book.getAvailableQuantity() != 0)
            theBook.setAvailableQuantity(book.getAvailableQuantity());
        if (book.getTotalQuantity() != 0)
            theBook.setTotalQuantity(book.getTotalQuantity());
        return bookRepository.save(theBook);
        }
        throw new CustomBadException("Id not found");
    }

    @Override
    public List<Books> searchBook(String query) {
        ExampleMatcher matcher = ExampleMatcher
                .matchingAny()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        String[] queryTerms = query.split(" "); // Split query into individual terms

        List<Books> resultBooks = new ArrayList<>();

        for (String term : queryTerms) {
            Books book = new Books();
            book.setBookAuthor(term);
            book.setBookTitle(term);
            book.setPublisher(term);
            book.setTotalQuantity(-1);
            book.setAvailableQuantity(-1);
            book.setYearPublication(term);


            Example<Books> example = Example.of(book, matcher);

            List<Books> termResults = bookRepository.findAll(example);
            resultBooks.addAll(termResults);
        }
        // Remove duplicates from the result
        return resultBooks.stream().distinct().collect(Collectors.toList());
        // Query Searching
        //return bookRepository.searchBook(query);

    }

}
