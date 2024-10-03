package com.junitTesting.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.junitTesting.enitites.Book;
import com.junitTesting.repositories.BookRepository;
import com.junitTesting.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper;
    ObjectWriter objectWriter;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    Book book1 = Book.builder().id(1L).name("Book 1").description("Book 1 description").count(2).build();
    Book book2 = Book.builder().id(2L).name("Book 2").description("Book 2 description").count(3).build();
    Book book3 = Book.builder().id(3L).name("Book 3").description("Book 3 description").count(8).build();
    Book book4 = Book.builder().id(4L).name("Book 4").description("Book 4 description").count(1).build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writer();
    }

    @Test
    void save() throws Exception {
        var book = Book.builder().id(5L).name("Book 5").description("Book 5 description").count(4).build();
        Mockito.when(bookService.save(book)).thenReturn(book);
        String content = objectMapper.writeValueAsString(book);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/junit-test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Book 5")));
    }

    @Test
    void update() throws Exception {
        var editBook = Book.builder().id(1L).name("Book 1 edited").description("Book 1 description edited").count(3).build();
        Mockito.when(bookService.update(1L, editBook)).thenReturn(editBook);
        String content = objectMapper.writeValueAsString(editBook);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/junit-test/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Book 1 edited")));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/junit-test/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        var books = Arrays.asList(book1, book2, book3, book4);
        Mockito.when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/junit-test")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].name", is("Book 4")));
    }

    @Test
    void findById() throws Exception{
        Mockito.when(bookService.findById(2L)).thenReturn(book2);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/junit-test/2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Book 2")));
    }

}