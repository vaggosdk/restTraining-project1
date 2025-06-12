package com.restTraining.project1.controller;

import com.restTraining.project1.request.BookRequest;
import com.restTraining.project1.entity.Book;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                new Book(1L, "Computer Science Pro", "Chad Darby", "Computer Science", 5),
                new Book(2L, "Java Spring Master", "Eric Roby", "Computer Science", 5),
                new Book(3L, "Why 1+1 Rocks", "Adil A.", "Math", 5),
                new Book(4L, "How Bears Hibernate", "Bob B.", "Science", 2),
                new Book(5L, "A Pirate's Treasure", "Curt C.", "History", 3),
                new Book(6L, "Why 2+2 is Better", "Dan D.", "Computer Science", 1)
        ));
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category) {
        if (category == null || category.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable @Min(value = 1) Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest newBook) {
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;

        Book book = convertToBook(newBook, id);

        books.add(book);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable @Min(value = 1)  Long id,@Valid @RequestBody BookRequest bookRequest) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                Book updatedBook = convertToBook(bookRequest, id);
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable @Min(value = 1)  Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    private Book convertToBook(BookRequest bookRequest, Long id) {
        return new Book(id, bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getCategory(), bookRequest.getRating());
    }
}
