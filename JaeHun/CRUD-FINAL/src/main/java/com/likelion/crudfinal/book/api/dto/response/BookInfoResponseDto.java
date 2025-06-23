package com.likelion.crudfinal.book.api.dto.response;

import com.likelion.crudfinal.author.api.dto.response.AuthorInfoResponseDto;
import com.likelion.crudfinal.book.domain.Book;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BookInfoResponseDto(
        Long id,
        String title,
        Integer publicationYear,
        AuthorInfoResponseDto author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static BookInfoResponseDto from(Book book) {
        AuthorInfoResponseDto authorDto = null;
        if (book.getAuthor() != null) {
            authorDto = AuthorInfoResponseDto.from(book.getAuthor());
        }

        return BookInfoResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .publicationYear(book.getPublicationYear())
                .author(authorDto)
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}