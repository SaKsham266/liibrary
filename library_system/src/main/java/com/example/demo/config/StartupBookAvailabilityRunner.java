package com.example.demo.config;

import com.example.demo.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupBookAvailabilityRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupBookAvailabilityRunner.class);
    private static final int TARGET_ISSUED_BOOKS = 5;

    private final LibraryService libraryService;

    public StartupBookAvailabilityRunner(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void run(String... args) {
        int seededBooks = libraryService.seedRandomIssuedBooks(TARGET_ISSUED_BOOKS);
        if (seededBooks > 0) {
            log.info("Marked {} book(s) as issued at startup to reach {} issued books.", seededBooks, TARGET_ISSUED_BOOKS);
        } else {
            log.info("Startup issued-book seeding skipped; target already satisfied or no available books.");
        }
    }
}
