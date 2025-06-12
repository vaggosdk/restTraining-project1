package com.restTraining.project1.controller;

import com.restTraining.project1.request.BookRequest;
import com.restTraining.project1.entity.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Book Rest API Endpoinst", description = "Operations related to books")
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

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getBooks(@Parameter(description = "Optional query parameter") @RequestParam(required = false) String category) {
        if (category == null || category.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @Operation(summary = "Get a book by Id", description = "Retrieve a specific book by Id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "Id of book to be retrieved") @PathVariable @Min(value = 1) Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest newBook) {
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;

        Book book = convertToBook(newBook, id);

        books.add(book);
    }

    @Operation(summary = "Update a book", description = "Update the details of an existing book")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateBook(@Parameter(description = "Id of the book to update") @PathVariable @Min(value = 1) Long id, @Valid @RequestBody BookRequest bookRequest) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                Book updatedBook = convertToBook(bookRequest, id);
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @Operation(summary = "Delete a book", description = "Remove a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //it is a good practice to use this annotation for delete operations, as it indicates that the request was successful and there is no content to return.
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Id of the book to delete") @PathVariable @Min(value = 1) Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    private Book convertToBook(BookRequest bookRequest, Long id) {
        return new Book(id, bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getCategory(), bookRequest.getRating());
    }
}
