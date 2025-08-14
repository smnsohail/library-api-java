package com.smn.library_api.controller;

import com.smn.library_api.dto.BorrowRequest;
import com.smn.library_api.dto.ReturnRequest;
import com.smn.library_api.model.BookTransaction;
import com.smn.library_api.model.User;
import com.smn.library_api.repository.UserRepository;
import com.smn.library_api.service.BookTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/transactions")
public class BookTransactionController {

    private final BookTransactionService service;
    private final UserRepository userRepository;

    public BookTransactionController(BookTransactionService service, UserRepository userRepository){
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping("/borrow")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')") // Both can borrow
    public ResponseEntity<BookTransaction> borrow(@RequestBody BorrowRequest req, Authentication auth){
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        BookTransaction tx = service.borrowBook(user.getId(), req.getBookId());
        return ResponseEntity.ok(tx);
    }

    @PostMapping("/return")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')") // Both can return
    public ResponseEntity<BookTransaction> doReturn(@RequestBody ReturnRequest req){
        BookTransaction tx = service.returnBook(req.getTransactionId());
        return ResponseEntity.ok(tx);
    }

    @GetMapping("/mine")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')") // Both can view own history
    public ResponseEntity<List<BookTransaction>> myHistory(Authentication auth){
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(service.getUserTransactions(user.getId()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can view all
    public ResponseEntity<List<BookTransaction>> all(){
        return ResponseEntity.ok(service.getAllTransactions());
    }
}
