package com.smn.library_api.controller;

import com.smn.library_api.model.Book;
import com.smn.library_api.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }


    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook){
        try {
            return ResponseEntity.ok(bookService.updateBook(id, updatedBook));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            Boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                return ResponseEntity.ok("Deleted Successfully");
            } else {
                return ResponseEntity.status(404).body("Book not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting Book: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category){
        if(title != null)
            return ResponseEntity.ok(bookService.searchByTitle(title));
        else if(author != null)
            return ResponseEntity.ok(bookService.searchByAuthor(author));
        else if(category != null)
            return ResponseEntity.ok(bookService.searchByCategory(category));
        else
            return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
