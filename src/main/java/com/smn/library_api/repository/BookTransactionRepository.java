package com.smn.library_api.repository;

import com.smn.library_api.model.BookTransaction;
import com.smn.library_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {
    List<BookTransaction> findByUser(User user);
}
