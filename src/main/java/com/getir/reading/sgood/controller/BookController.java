package com.getir.reading.sgood.controller;

import com.getir.reading.sgood.exception.EntityNotFoundException;
import com.getir.reading.sgood.exception.StockIsNegativeException;
import com.getir.reading.sgood.model.Book;
import com.getir.reading.sgood.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //main methods
    @PostMapping("/save")
    public ResponseEntity<Book> create(@Valid @RequestBody Book book){
        try{
            Book savedBook = bookService.saveBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/updateStock/{id}/{purchasedBookCount}")
    public ResponseEntity<Book> updateStock(@PathVariable("id") int id,
                                            @PathVariable("purchasedBookCount") int purchasedBookCount)
                                            throws EntityNotFoundException, StockIsNegativeException{
        Optional<Book> bookOptional = bookService.findBookById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            int stock = book.getStock();
            if(stock - purchasedBookCount < 0){
                throw new StockIsNegativeException("Stock is: " + stock + ", therefore, " + purchasedBookCount
                        + "can not be bought!");
            }
            book.setStock(stock - purchasedBookCount);
            return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.OK);
        }else{
            throw new EntityNotFoundException("Book is not exist!");
        }
    }

    //Not main methods
    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAll(){
        List<Book> books = bookService.findAll();
        try {
            if (books.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(books, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        try{
            bookService.deleteBookById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
