package com.domain.library.repository;

import com.domain.library.dto.BooksDTO;
import com.domain.library.entity.Books;

import java.util.List;


public interface BookRepositoryCustom {
    boolean isBookExist(Books book);
    Books increaseTotalQuantity(int id);
    Books decreaseTotalQuantity(int id);
    Books addNewBook(Books book);
    Books deleteSingleBook(int id);
    int increaseAvailableQuantity(int id);
    int decreaseAvailableQuantity(int id);

}
