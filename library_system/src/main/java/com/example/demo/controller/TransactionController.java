package com.example.demo.controller;

import com.example.demo.dto.IssueBookRequest;
import com.example.demo.model.Transaction;
import com.example.demo.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final LibraryService libraryService;

    public TransactionController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // POST /api/transactions/issue  → replaces case 3: issueBook()
    // Body: { "bookId": 1, "memberId": 1 }
    @PostMapping("/issue")
    public ResponseEntity<Transaction> issueBook(@Valid @RequestBody IssueBookRequest request) {
        return ResponseEntity.ok(libraryService.issueBook(request.bookId(), request.memberId()));
    }

    // PUT /api/transactions/return/{bookId}  → replaces case 4: returnBook()
    @PutMapping("/return/{bookId}")
    public ResponseEntity<Transaction> returnBook(@PathVariable Integer bookId) {
        return ResponseEntity.ok(libraryService.returnBook(bookId));
    }

    // GET /api/transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(libraryService.getAllTransactions());
    }
}



