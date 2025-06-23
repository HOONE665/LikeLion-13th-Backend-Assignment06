package com.likelion.crudfinal.author.api.dto.response;

import com.likelion.crudfinal.author.domain.Author;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AuthorInfoResponseDto(
        Long id,
        String name,
        String biography,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AuthorInfoResponseDto from(Author author) {
        return AuthorInfoResponseDto.builder()
                .id(author.getId())
                .name(author.getName())
                .biography(author.getBiography())
                .createdAt(author.getCreatedAt())
                .updatedAt(author.getUpdatedAt())
                .build();
    }
}