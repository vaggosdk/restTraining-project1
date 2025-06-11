package com.restTraining.project1.controller;

import com.restTraining.project1.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                new Book("Tiitle one", "Author one", "science"),
                new Book("Tiitle two", "Author two", "science"),
                new Book("Tiitle three", "Author three", "history"),
                new Book("Tiitle four", "Author four", "math"),
                new Book("Tiitle five", "Author five", "math"),
                new Book("Tiitle six", "Author six", "math")
        ));
    }

    @GetMapping("/api/books")
    public List<Book> getBooks() {
        return books;
    }
}
