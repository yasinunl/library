package com.domain.library.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "borrowings")
public class Borrowings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "borrow_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date borrowDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "students_id")
    @JsonIgnoreProperties("borrowings")
    private Students student;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "books_id")
    @JsonIgnoreProperties("borrowings")
    private Books books;

    public Borrowings() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Borrowings{" +
                "id=" + id +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", student=" + student +
                ", books=" + books +
                '}';
    }
}
