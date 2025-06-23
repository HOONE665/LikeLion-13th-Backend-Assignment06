package com.likelion.crudfinal.book.domain.repository;

import com.likelion.crudfinal.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}