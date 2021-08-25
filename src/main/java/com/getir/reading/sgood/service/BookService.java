package com.getir.reading.sgood.service;

import com.getir.reading.sgood.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);
    Optional<Book> findBookById(int id);
    List<Book> findAll();
    void deleteBookById(int id);
}
