package com.junitTesting.services;

import com.junitTesting.enitites.Book;
import com.junitTesting.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    @Autowired
    private BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public Book update(Long id, Book book) {
        if (id == null || book.getId() == null) {
            throw new RuntimeException("Something wrong.!");
        }
        return bookRepository.findById(id).map(b -> {
            b.setName(book.getName());
            b.setDescription(book.getDescription());
            b.setCount(book.getCount());
            return bookRepository.save(b);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public String delete(Long id) {
        if (id == null) {
            throw new RuntimeException("Something wrong.!");
        }
        bookRepository.deleteById(id);
        return "Successfully deleted.";
    }
}
