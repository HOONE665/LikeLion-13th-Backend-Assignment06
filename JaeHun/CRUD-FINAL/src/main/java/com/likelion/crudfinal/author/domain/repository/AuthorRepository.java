package com.likelion.crudfinal.author.domain.repository;

import com.likelion.crudfinal.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}