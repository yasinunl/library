package com.domain.library.repository;

import com.domain.library.dto.BooksDTO;
import com.domain.library.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Books, Integer>, BookRepositoryCustom, PagingAndSortingRepository<Books, Integer> {
   /* @Query(value = "SELECT * FROM BOOKS " +
            "WHERE MATCH (book_title, book_author, publisher, year_of_publication) AGAINST (:query'*'  IN BOOLEAN MODE)",
            nativeQuery = true)
    List<Books> searchBook(@Param("query") String query);*/
    @Query(nativeQuery = true,
            value = "select ceil(count(*) / 2) from books")
    Integer countBooks();
}
