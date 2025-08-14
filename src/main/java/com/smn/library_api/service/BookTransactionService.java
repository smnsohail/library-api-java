package com.smn.library_api.service;

import com.smn.library_api.model.Book;
import com.smn.library_api.model.BookTransaction;
import com.smn.library_api.model.User;
import com.smn.library_api.repository.BookRepository;
import com.smn.library_api.repository.BookTransactionRepository;
import com.smn.library_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookTransactionService {

    private final BookTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BookTransactionService(BookTransactionRepository transactionRepository,
                                  UserRepository userRepository,
                                  BookRepository bookRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public BookTransaction borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available currently");
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        BookTransaction transaction = new BookTransaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setBorrowDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusWeeks(2)); // due in 2 weeks
        transaction.setReturnDate(null);
        transaction.setFine(0.0);

        return transactionRepository.save(transaction);
    }

    public BookTransaction returnBook(Long transactionId) {
        BookTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        transaction.setReturnDate(LocalDate.now());

        Book book = transaction.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        // TODO: Fine calculation skipped as per request
        transaction.setFine(0.0);

        return transactionRepository.save(transaction);
    }

    public List<BookTransaction> getUserTransactions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findByUser(user);
    }

    public List<BookTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
