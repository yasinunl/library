package com.domain.library.repository;

import com.domain.library.entity.Borrowings;
import com.domain.library.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Integer> {
    @Query(value = "SELECT * FROM STUDENTS " +
            "WHERE MATCH (first_name, last_name, email) AGAINST (:query'*'  IN BOOLEAN MODE)",
            nativeQuery = true)
    List<Students> searchStudent(@Param("query") String query);

    @Query(
            value = "select * from borrowings where students_id = :query",
            nativeQuery = true
    )
    List<Borrowings> getBorrowingsWithStudentId(@Param("query") int id);
}
