armanpackage com.arman.reading.sgood.service.impl;


import com.arman.reading.sgood.model.Book;
import com.arman.reading.sgood.repository.BookRepo;
import com.arman.reading.sgood.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepository;

    public BookServiceImpl(BookRepo bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findBookById(int id){
        Optional<Book> book = bookRepository.findById(id);
        return book;
    }

    @Override
    public List<Book> findAll(){
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public void deleteBookById(int id){
        bookRepository.deleteById(id);
    }
}
