package com.junitTesting.services;

import com.junitTesting.enitites.Book;
import com.junitTesting.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void save() {
        Book book = Book.builder().name("book 1").description("book 1 description").count(3).build();

        Mockito.when(bookRepository.save(book)).thenReturn(book);

        Book svgBook = bookService.save(book);

        assertEquals(book.getName(),svgBook.getName());
        assertEquals(book.getDescription(),svgBook.getDescription());
        assertEquals(book.getCount(),svgBook.getCount());
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    void findAll() {
        List<Book> books = Arrays.asList(new Book(1L,"book 1","book 1 description",3),
                new Book(2L,"book 2","book 2 description",4));

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> booksList = bookService.findAll();

        assertEquals(books.size(), booksList.size());
        Mockito.verify(bookRepository,Mockito.times(1)).findAll();

    }

    @Test
    void findById() {
        Long studentId = 1L;
        Book book = Book.builder().name("book 1").description("book 1 description").count(3).build();
       book.setId(studentId);
        Mockito.when(bookRepository.findById(studentId)).thenReturn(Optional.of(book));

        Book returnBook = bookService.findById(studentId);

        assertEquals(book.getName(),returnBook.getName());
        assertEquals(book.getDescription(),returnBook.getDescription());
        assertEquals(book.getCount(),returnBook.getCount());
        Mockito.verify(bookRepository,Mockito.times(1)).findById(studentId);
    }

    @Test
    void update() {
        Long studentId = 1L;
        Book book = Book.builder().name("book 1").description("book 1 description").count(3).build();
        Book editedBook = Book.builder().id(studentId).name("book 1 edited").description("book 1 description edited").count(4).build();
        book.setId(studentId);

       Mockito.when(bookRepository.findById(studentId)).thenReturn(Optional.of(book));
       Mockito.when(bookRepository.save(editedBook)).thenReturn(editedBook);

       Book updatedBook = bookService.update(studentId,editedBook);

       assertEquals(editedBook.getName(), updatedBook.getName());
       assertEquals(editedBook.getDescription(), updatedBook.getDescription());
       assertEquals(editedBook.getCount(), updatedBook.getCount());
       Mockito.verify(bookRepository, Mockito.times(1)).findById(studentId);
       Mockito.verify(bookRepository, Mockito.times(1)).save(editedBook);


    }

    @Test
    void delete() {
        Long studentId = 1l;
        Book book = Book.builder().name("book 1").description("book 1 description").count(3).build();
        book.setId(studentId);

        Mockito.when(bookRepository.findById(studentId)).thenReturn(Optional.of(book));

        String result = bookService.delete(studentId);

        Mockito.verify(bookRepository,Mockito.times(1)).deleteById(studentId);
        assertEquals("Successfully deleted.",result);
    }
}