package com.domain.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Books{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "year_of_publication")
    private String yearPublication;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @JsonIgnoreProperties("books")
    @OneToMany(mappedBy = "books",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Borrowings> borrowings;

    public void addBorrowing(Borrowings borrowing){
        if(borrowings == null) borrowings = new ArrayList<Borrowings>();
        borrowings.add(borrowing);
        borrowing.setBooks(this);
    }

    public Books() {
    }

    public Books(String bookTitle, String bookAuthor, String yearPublication, String publisher) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.yearPublication = yearPublication;
        this.publisher = publisher;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getYearPublication() {
        return yearPublication;
    }

    public void setYearPublication(String yearPublication) {
        this.yearPublication = yearPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<Borrowings> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowings> borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", yearPublication=" + yearPublication +
                ", publisher='" + publisher + '\'' +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
