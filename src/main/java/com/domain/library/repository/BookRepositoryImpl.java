package com.domain.library.repository;

import com.domain.library.dto.BooksDTO;
import com.domain.library.entity.Books;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom{
    EntityManager entityManager;

    @Autowired
    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isBookExist(Books book) {
        TypedQuery<Books> typedQuery = entityManager.createQuery("select b from Books b " +
                "where b.bookTitle = :title and b.bookAuthor = :author", Books.class);
        typedQuery.setParameter("title", book.getBookTitle());
        typedQuery.setParameter("author", book.getBookAuthor());

        List<Books> books = typedQuery.getResultList();
        return !books.isEmpty();
    }

    @Override
    public Books increaseTotalQuantity(int id) {
        Books book = entityManager.find(Books.class, id);
        book.setTotalQuantity(book.getTotalQuantity() + 1);
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        return book;
    }
    @Override
    public Books decreaseTotalQuantity(int id) {
        Books book = entityManager.find(Books.class, id);
        book.setTotalQuantity(book.getTotalQuantity() - 1);
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        return book;
    }
    @Override
    public int increaseAvailableQuantity(int id) {
        return entityManager.find(Books.class, id).getAvailableQuantity() + 1;
    }
    @Override
    public int decreaseAvailableQuantity(int id) {
        return entityManager.find(Books.class, id).getAvailableQuantity() - 1;
    }



    @Override
    @Transactional
    public Books addNewBook(Books book) {
        if (book.getId() == 0) {
            if (isBookExist(book)){
                TypedQuery<Books> typedQuery = entityManager.createQuery("select b from Books b " +
                        "where b.bookTitle = :title and b.bookAuthor = :author", Books.class);
                typedQuery.setParameter("title", book.getBookTitle());
                typedQuery.setParameter("author", book.getBookAuthor());
                book.setId(typedQuery.getSingleResult().getId());
                book = increaseTotalQuantity(book.getId());
            }
            else{
                if(book.getTotalQuantity() == 0) {
                    book.setAvailableQuantity(1);
                    book.setTotalQuantity(1);
                }
            }
        }
        return entityManager.merge(book);
    }

    @Override
    @Transactional
    public Books deleteSingleBook(int id) {
        Books book = decreaseTotalQuantity(id);
        if(book.getTotalQuantity() < 1){
            entityManager.remove(book);
            return book;
        }
        return entityManager.merge(book);
    }
    /*
    @Override
    public List<Books> searchBook(String query) {
       String[] queryList = query.split("-", -1);
        query = query.replace("-" , " ");
        System.out.println(queryList[1]);
        StringBuilder typedQueryString = new StringBuilder("select b from Books b where b.bookAuthor like '%"+ query +"%' " +
                "or b.bookTitle like '%"+ query +"%' " +
                "or b.yearOfPublication like '%"+ a +"%' " +
                "or b.publisher like '%"+ query +"%' ");
        for(String a : queryList) {
            typedQueryString.append("or b.bookTitle like '%"+ a +"%' " +
                    "or b.bookAuthor like '%"+ a +"%' " +
                    "or b.yearOfPublication like '%"+ a +"%' " +
                    "or b.publisher like '%"+ a +"%' ");
        }
        TypedQuery<Books> typedQuery = entityManager.createQuery(typedQueryString.toString(), Books.class);
        return typedQuery.getResultList();
    }*/
}
