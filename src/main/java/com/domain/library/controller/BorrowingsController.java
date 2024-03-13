package com.domain.library.controller;

import com.domain.library.entity.Borrowings;
import com.domain.library.service.BorrowingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowings")
public class BorrowingsController {
    BorrowingsService borrowingsService;

    @Autowired
    public BorrowingsController(BorrowingsService borrowingsService) {
        this.borrowingsService = borrowingsService;
    }

    @GetMapping
    public List<Borrowings> getAllBorrowings(){
        return borrowingsService.getAll();
    }
    @GetMapping("/{id}")
    public Borrowings getById(@PathVariable int id){
        return borrowingsService.getById(id);
    }
    @PostMapping
    public Borrowings addBorrowing(@RequestBody Borrowings borrowing){
        return borrowingsService.addBorrowing(borrowing);
    }
    @DeleteMapping("/{id}")
    public Borrowings deleteBorrowing(@PathVariable int id){
        return borrowingsService.deleteBorrowing(id);
    };
    @PutMapping
    public Borrowings updateBorrowing(@RequestBody Borrowings borrowing){
        return borrowingsService.updateBorrowing(borrowing);
    }
}
