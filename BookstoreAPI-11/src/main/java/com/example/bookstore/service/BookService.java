package com.example.bookstore.service;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final Counter bookCreationCounter;

    @Autowired
    public BookService(MeterRegistry meterRegistry) {
        this.bookCreationCounter = meterRegistry.counter("bookstore.books.created");
    }

    public BookDTO createBook(BookDTO bookDTO) {
        // Your logic to create a book
        bookCreationCounter.increment(); // Increment the custom metric
        return bookDTO;
    }


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

