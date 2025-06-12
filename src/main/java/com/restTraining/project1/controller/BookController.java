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

    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
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

    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }
}
