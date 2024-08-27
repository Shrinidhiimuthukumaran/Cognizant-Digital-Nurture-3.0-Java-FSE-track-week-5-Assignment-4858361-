package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Book;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        return BookMapper.INSTANCE.bookToBookDTO(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
            .map(BookMapper.INSTANCE::bookToBookDTO)
            .collect(Collectors.toList());
    }

    public BookDTO saveBook(BookDTO bookDTO) {
        Book book = BookMapper.INSTANCE.bookDTOToBook(bookDTO);
        book = bookRepository.save(book);
        return BookMapper.INSTANCE.bookToBookDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        BookMapper.INSTANCE.bookDTOToBook(bookDTO);
        bookRepository.save(existingBook);
        return BookMapper.INSTANCE.bookToBookDTO(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

