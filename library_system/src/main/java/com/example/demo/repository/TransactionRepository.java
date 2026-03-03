package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // Finds the active (unreturned) transaction for a book
    Optional<Transaction> findByBook_BookIdAndReturnDateIsNull(Integer bookId);

    long countByReturnDateIsNull();
}
