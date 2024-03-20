package com.domain.library.service;

import com.domain.library.dto.BooksDTO;
import com.domain.library.entity.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Books> findAll(Pageable pageable);
    Books findById(int id);
    Books save(Books book);
    Books deleteSingleBook(int id);
    Books deleteAllBooks(Books book);
    Books updateTheBook(Books book);
    List<Books> searchBook(String query);
    List<Books> findOnlyBooks();
    List<BooksDTO> pageableBooks(Pageable pageable);
    Integer pageCount();
}
