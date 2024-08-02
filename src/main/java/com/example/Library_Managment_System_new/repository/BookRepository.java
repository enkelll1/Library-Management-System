package com.example.Library_Managment_System_new.repository;

import com.example.Library_Managment_System_new.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;

public interface BookRepository extends MongoRepository<Book, String> {
    @Query("{ 'tags': { $all: ?0 } }")
    List<Book> findByTags(Set<String> tags);
}