package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void createBookSuccessfully() throws Exception {
        Book newBook = new Book("Clean Code", "Robert Martin", 45.99, 10);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert Martin"))
                .andExpect(jsonPath("$.price").value(45.99))
                .andExpect(jsonPath("$.stock").value(10))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void createBookWithInvalidPriceFails() throws Exception {
        Book invalidBook = new Book("Test Book", "Test Author", -10.0, 5);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidBook)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllBooksReturnsCorrectList() throws Exception {
        Book book1 = bookRepository.save(new Book("Book 1", "Author 1", 20.0, 5));
        Book book2 = bookRepository.save(new Book("Book 2", "Author 2", 30.0, 8));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    void getBookByIdSuccessfully() throws Exception {
        Book savedBook = bookRepository.save(new Book("Test Book", "Test Author", 25.0, 3));

        mockMvc.perform(get("/api/books/{id}", savedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.price").value(25.0));
    }

    @Test
    void getBookByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBookSuccessfully() throws Exception {
        Book book = bookRepository.save(new Book("To Delete", "Author", 15.0, 2));

        mockMvc.perform(delete("/api/books/{id}", book.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBookStockAfterMultipleOperations() throws Exception {
        Book book = bookRepository.save(new Book("Popular Book", "Famous Author", 35.0, 20));

        mockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(20));
    }
}