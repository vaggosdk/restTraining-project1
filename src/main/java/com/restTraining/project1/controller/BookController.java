package com.restTraining.project1.controller;

import com.restTraining.project1.entity.Book;
import org.springframework.web.bind.annotation.*;

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
                new Book("Title one", "Author one", "science"),
                new Book("Title two", "Author two", "science"),
                new Book("Title three", "Author three", "history"),
                new Book("Title four", "Author four", "math"),
                new Book("Title five", "Author five", "math"),
                new Book("Title six", "Author six", "math")
        ));
    }

    @GetMapping("/api/books")
    public List<Book> getBooks(@RequestParam (required = false) String category ) {
        if (category == null || category.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/api/books/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/api/books")
    public void createBook(@RequestBody Book newBook) {
        boolean isNewBook = books.stream()
                .noneMatch(existingBook -> existingBook.getTitle().equalsIgnoreCase(newBook.getTitle()));

        if (isNewBook){
            books.add(newBook);
        }
    }

    @PutMapping("/api/books/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                books.set(i, updatedBook);
                return;
            }
        }
    }
}
