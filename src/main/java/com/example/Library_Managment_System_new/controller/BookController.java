package com.example.Library_Managment_System_new.controller;

import com.example.Library_Managment_System_new.dto.SearchBookRequest;
import com.example.Library_Managment_System_new.dto.StoreBookRequest;
import com.example.Library_Managment_System_new.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {
    // This field injection is used only for local compiling reasons
    @Autowired
    BookService bookService;

    @PostMapping("/store")
    public ResponseEntity<String> storeBook(@Valid @RequestBody StoreBookRequest storeBookRequest) {
        System.out.println("job");
        String response = bookService.storeBook(storeBookRequest.getIsbn(), storeBookRequest.getTags());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchBooks(@Valid @RequestBody SearchBookRequest request) {
        List<String> response = bookService.searchBooks(request);
        return ResponseEntity.ok(response);
    }
}

