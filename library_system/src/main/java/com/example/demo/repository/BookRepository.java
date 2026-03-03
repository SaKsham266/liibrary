
package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    long countByAvailableFalse();

    long countByAvailableTrue();

    List<Book> findByAvailableTrue();

    @Query("SELECT b FROM Book b WHERE b.available = true ORDER BY function('RAND')")
    List<Book> findRandomAvailableBooks(Pageable pageable);
}
