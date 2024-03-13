package com.domain.library.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "borrowed_books")
    private int borrowedBooks;

    @JsonIgnoreProperties("student")
    @OneToMany(mappedBy = "student",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Borrowings> borrowings;

    public List<Borrowings> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowings> borrowings) {
        this.borrowings = borrowings;
    }

    public void addBorrowing(Borrowings borrowing){
        if(borrowings == null) borrowings = new ArrayList<Borrowings>();
        borrowings.add(borrowing);
        borrowing.setStudent(this);
    }

    public Students() {
    }

    public Students(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(int borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public String toString() {
        return "Students{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }
}
