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

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        validateBook(book);
        return bookRepository.save(book);
    }

    private void validateBook(Book book) {
        if (book == null) {
            throw new BadRequestException("El libro no puede ser nulo");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new BadRequestException("El título del libro es obligatorio");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new BadRequestException("El autor del libro es obligatorio");
        }
        if (book.getPrice() <= 0) {
            throw new BadRequestException(String.format("El precio debe ser mayor que 0, valor recibido: %.2f", book.getPrice()));
        }
        if (book.getStock() < 0) {
            throw new BadRequestException(String.format("El stock no puede ser negativo, valor recibido: %d", book.getStock()));
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}