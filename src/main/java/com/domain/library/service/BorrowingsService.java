package com.domain.library.service;

import com.domain.library.entity.Borrowings;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BorrowingsService {
    List<Borrowings> getAll();
    Borrowings addBorrowing(Borrowings borrowing, HttpServletRequest request);
    Borrowings deleteBorrowing(int id);
    Borrowings getById(int id);
    Borrowings updateBorrowing(Borrowings borrowing);
}
