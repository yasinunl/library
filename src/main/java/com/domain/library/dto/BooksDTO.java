package com.domain.library.dto;

import com.domain.library.entity.Borrowings;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class BooksDTO {
    @Id
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

    public BooksDTO() {
    }

    public BooksDTO(String bookTitle, String bookAuthor, String yearPublication, String publisher) {
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
