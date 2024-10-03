package com.junitTesting.controllers;

import com.junitTesting.enitites.Book;
import com.junitTesting.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("junit-test")
public class BookController {


    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> saveHandler(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.save(book));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateHandler(@PathVariable("id") Long id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.update(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHandler(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.delete(id));
    }
}
