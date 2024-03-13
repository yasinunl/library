package com.domain.library.repository;

import com.domain.library.entity.Borrowings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingsRepository extends JpaRepository<Borrowings, Integer> {
}
