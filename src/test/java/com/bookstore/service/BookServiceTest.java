package com.bookstore.service;

import com.bookstore.exception.BadRequestException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test: precio <= 0 lanza BadRequestException
    @Test
    void saveBookPriceMustBePositive() {
        Book invalidBook = new Book("Titulo", "Autor", -5.0, 3);

        assertThrows(BadRequestException.class, () -> bookService.saveBook(invalidBook));
        verify(bookRepository, never()).save(any(Book.class));
    }

    // Test: guardar libro correctamente
    @Test
    void saveBookSuccessfully() {
        Book book = new Book("Titulo", "Autor", 10.0, 5);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book saved = bookService.saveBook(book);

        assertNotNull(saved);
        assertEquals("Titulo", saved.getTitle());
        assertEquals("Autor", saved.getAuthor());
        assertEquals(10.0, saved.getPrice());
        assertEquals(5, saved.getStock());
    }

    // Test extra: guardar libro con stock 0
    @Test
    void saveBookWithDifferentStock() {
        Book book = new Book("Otro titulo", "Otro autor", 20.0, 0);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book saved = bookService.saveBook(book);

        assertNotNull(saved);
        assertEquals(0, saved.getStock());
    }

    // Test: obtener libro por ID encontrado
    @Test
    void getByIdFound() {
        Book book = new Book("Titulo", "Autor", 10.0, 5);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book found = bookService.getById(1L);

        assertNotNull(found);
        assertEquals("Titulo", found.getTitle());
        assertEquals("Autor", found.getAuthor());
    }

    // Test: obtener libro por ID no encontrado
    @Test
    void getByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getById(1L));
    }

    // Test: eliminar libro por ID
    @Test
    void deleteByIdTest() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    // Test extra: obtener todos los libros
    @Test
    void getAllBooksTest() {
        List<Book> books = List.of(
                new Book("Libro1", "Autor1", 15.0, 2),
                new Book("Libro2", "Autor2", 20.0, 5)
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Libro1", result.get(0).getTitle());
        assertEquals("Libro2", result.get(1).getTitle());
    }
}