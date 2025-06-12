package com.restTraining.project1.controller;

import com.restTraining.project1.entity.Book;
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
                new Book(2L,"Java Spring Master", "Eric Roby", "Computer Science", 5),
                new Book(3L,"Why 1+1 Rocks", "Adil A.", "Math",5),
                new Book(4L,"How Bears Hibernate", "Bob B.", "Science",2),
                new Book(5L,"A Pirate's Treasure", "Curt C.", "History",3),
                new Book(6L,"Why 2+2 is Better", "Dan D.", "Computer Science",1)
        ));
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam (required = false) String category ) {
        if (category == null || category.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public void createBook(@RequestBody Book newBook) {
        boolean isNewBook = books.stream()
                .noneMatch(existingBook -> existingBook.getTitle().equalsIgnoreCase(newBook.getTitle()));

        if (isNewBook){
            books.add(newBook);
        }
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}
