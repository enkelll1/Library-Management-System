package com.example.Library_Managment_System_new.service;

import com.example.Library_Managment_System_new.dto.SearchBookRequest;
import com.example.Library_Managment_System_new.model.Book;
import com.example.Library_Managment_System_new.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String storeBook(String isbn, Set<String> tags) {
        Book book = new Book(isbn, tags);
        bookRepository.save(book);
        return "Ok";
    }

    public List<String> searchBooks(SearchBookRequest request) {
        List<Book> books = bookRepository.findByTags(request.getSearch());
        if (books.isEmpty()) {
            return List.of("No books found");
        }
        return books.stream().map(Book::getIsbn).collect(Collectors.toList());
    }
}

