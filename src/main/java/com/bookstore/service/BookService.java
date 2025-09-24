package com.bookstore.service;

import com.bookstore.exception.BadRequestException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    // regla: precio > 0 antes de guardar
    public Book saveBook(Book book) {
        if (book.getPrice() <= 0) throw new BadRequestException("El precio debe ser mayor que 0");
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() { return bookRepository.findAll(); }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
    }

    public void deleteById(Long id) { bookRepository.deleteById(id); }
}