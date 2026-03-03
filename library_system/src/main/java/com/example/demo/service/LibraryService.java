package com.example.demo.service;
import com.example.demo.model.Book;
import com.example.demo.model.Member;
import com.example.demo.model.Transaction;

import com.example.demo.repository.BookRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.TransactionRepository;

import com.example.demo.exception.LibraryException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final TransactionRepository transactionRepository;

    // Constructor injection (best practice over @Autowired on fields)
    public LibraryService(BookRepository bookRepository,
                          MemberRepository memberRepository,
                          TransactionRepository transactionRepository) {
        this.bookRepository = Objects.requireNonNull(bookRepository, "bookRepository must not be null");
        this.memberRepository = Objects.requireNonNull(memberRepository, "memberRepository must not be null");
        this.transactionRepository = Objects.requireNonNull(transactionRepository, "transactionRepository must not be null");
    }

    // ─── BOOKS ────────────────────────────────────────────────────────────────

    public Book addBook(Book book) {
        Objects.requireNonNull(book, "book must not be null");
        book.setAvailable(true); // always true for a new book
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public long getTotalBooks() {
        return bookRepository.count();
    }

    public long getAvailableBooksCount() {
        return bookRepository.countByAvailableTrue();
    }

    public Book getBookById(Integer bookId) {
        Integer validatedBookId = Objects.requireNonNull(bookId, "bookId must not be null");
        return bookRepository.findById(validatedBookId)
                .orElseThrow(() -> new LibraryException("Book not found with ID: " + bookId));
    }

    @Transactional
    public Book updateBook(Integer bookId, Book updatedBook) {
        Integer validatedBookId = Objects.requireNonNull(bookId, "bookId must not be null");
        Objects.requireNonNull(updatedBook, "updatedBook must not be null");

        Book existingBook = getBookById(validatedBookId);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublisher(updatedBook.getPublisher());
        if (updatedBook.getAvailable() != null) {
            existingBook.setAvailable(updatedBook.getAvailable());
        }
        return bookRepository.save(existingBook);
    }

    @Transactional
    public void deleteBook(Integer bookId) {
        Book book = getBookById(bookId);
        if (!Boolean.TRUE.equals(book.getAvailable())) {
            throw new LibraryException("Issued books cannot be deleted.");
        }
        bookRepository.delete(book);
    }

    // ─── MEMBERS ──────────────────────────────────────────────────────────────

    public Member registerMember(Member member) {
        Objects.requireNonNull(member, "member must not be null");
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public long getTotalMembers() {
        return memberRepository.count();
    }

    public Member getMemberById(Integer memberId) {
        Integer validatedMemberId = Objects.requireNonNull(memberId, "memberId must not be null");
        return memberRepository.findById(validatedMemberId)
                .orElseThrow(() -> new LibraryException("Member not found with ID: " + memberId));
    }

    // ─── TRANSACTIONS ─────────────────────────────────────────────────────────

    /**
     * Issues a book to a member.
     * Mirrors the issueBook() logic from your original LibraryManager,
     * now using @Transactional instead of manual conn.setAutoCommit(false).
     */
    @Transactional
    public Transaction issueBook(Integer bookId, Integer memberId) {
        Integer validatedBookId = Objects.requireNonNull(bookId, "bookId must not be null");
        Integer validatedMemberId = Objects.requireNonNull(memberId, "memberId must not be null");

        Book book = bookRepository.findById(validatedBookId)
                .orElseThrow(() -> new LibraryException("Book not found with ID: " + bookId));

        if (!Boolean.TRUE.equals(book.getAvailable())) {
            throw new LibraryException("Book is not available for issue.");
        }

        Member member = memberRepository.findById(validatedMemberId)
                .orElseThrow(() -> new LibraryException("Member not found with ID: " + memberId));

        // Mark book as unavailable
        book.setAvailable(false);
        bookRepository.save(book);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setIssueDate(LocalDate.now());

        return transactionRepository.save(transaction);
    }

    /**
     * Returns a book.
     * Mirrors the returnBook() logic from your original LibraryManager.
     */
    @Transactional
    public Transaction returnBook(Integer bookId) {
        Integer validatedBookId = Objects.requireNonNull(bookId, "bookId must not be null");

        // Find the active (unreturned) transaction for this book
        Transaction transaction = transactionRepository
                .findByBook_BookIdAndReturnDateIsNull(validatedBookId)
                .orElseThrow(() -> new LibraryException("No active transaction found for book ID: " + bookId));

        // Set return date
        transaction.setReturnDate(LocalDate.now());
        transactionRepository.save(transaction);

        // Mark book as available again
        Book book = Objects.requireNonNull(transaction.getBook(), "Transaction must reference a book");
        book.setAvailable(true);
        bookRepository.save(book);

        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public long getIssuedBooksCount() {
        return bookRepository.countByAvailableFalse();
    }

    @Transactional
    public int seedRandomIssuedBooks(int targetIssuedCount) {
        if (targetIssuedCount <= 0) {
            return 0;
        }

        long currentlyIssued = getIssuedBooksCount();
        if (currentlyIssued >= targetIssuedCount) {
            return 0;
        }

        int booksNeeded = (int) Math.min(targetIssuedCount - currentlyIssued, getAvailableBooksCount());
        if (booksNeeded <= 0) {
            return 0;
        }

        List<Book> randomAvailableBooks = bookRepository.findRandomAvailableBooks(PageRequest.of(0, booksNeeded));
        if (randomAvailableBooks.size() < booksNeeded) {
            randomAvailableBooks = bookRepository.findByAvailableTrue();
            Collections.shuffle(randomAvailableBooks);
            randomAvailableBooks = randomAvailableBooks.subList(0, Math.min(booksNeeded, randomAvailableBooks.size()));
        }

        randomAvailableBooks.forEach(book -> book.setAvailable(false));
        bookRepository.saveAll(randomAvailableBooks);
        return randomAvailableBooks.size();
    }
}
