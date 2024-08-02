package com.example.Library_Managment_System_new;


import com.example.Library_Managment_System_new.controller.BookController;
import com.example.Library_Managment_System_new.dto.SearchBookRequest;
import com.example.Library_Managment_System_new.dto.StoreBookRequest;
import com.example.Library_Managment_System_new.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

@WebMvcTest(BookController.class)
public class BookTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;



    @Test
    void storeBookWithValidRequestShouldReturnOk() throws Exception {
        StoreBookRequest request = new StoreBookRequest();
        request.setIsbn("2041232349872");
        request.setTags(Set.of("Science"));

        when(bookService.storeBook(request.getIsbn(),request.getTags())).thenReturn("Ok");

        mockMvc.perform(post("/books/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Ok"));
    }

    @Test
    void storeBookWithoutTagsShouldReturnError() throws Exception {
        StoreBookRequest request = new StoreBookRequest();
        request.setIsbn("2041232349872");

        mockMvc.perform(post("/books/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error tags\":\"tags must not be empty.\"}"));
    }

    @Test
    void storeBookWithoutIsbnShouldReturnError() throws Exception {
        StoreBookRequest request = new StoreBookRequest();
        request.setTags(Set.of("Science"));

        mockMvc.perform(post("/books/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error isbn\":\"isbn must not be empty.\"}"));
    }

    @Test
    void storeBookWithWrongIsbnFormatShouldReturnError() throws Exception {
        StoreBookRequest request = new StoreBookRequest();
        request.setTags(Set.of("Science"));
        request.setIsbn("204123234982");

        mockMvc.perform(post("/books/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error isbn\":\"ISBN must be a 13-digit number.\"}"));
    }

    @Test
    void storeBookWithWrongIsbnAndWithoutTag() throws Exception {
        StoreBookRequest request = new StoreBookRequest();
        request.setIsbn("204123234982");

        mockMvc.perform(post("/books/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error isbn\":\"ISBN must be a 13-digit number.\",\"error tags\":\"tags must not be empty.\"}"));
    }


    @Test
    void searchBookWithValidRequestShouldReturnBooks() throws Exception {
        SearchBookRequest request = new SearchBookRequest();
        request.setSearch(Set.of("Science"));

        when(bookService.searchBooks(request)).thenReturn(List.of("1111111111111", "1111111111112"));

        mockMvc.perform(get("/books/search")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void searchBookWithoutTagsShouldReturnError() throws Exception {
        SearchBookRequest request = new SearchBookRequest();

        mockMvc.perform(get("/books/search")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error search\":\"no tags provided.\"}"));
    }
}

