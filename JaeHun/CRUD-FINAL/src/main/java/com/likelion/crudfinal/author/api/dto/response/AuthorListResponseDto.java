package com.likelion.crudfinal.author.api.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;
import java.util.List;

@Builder
public record AuthorListResponseDto(
        List<AuthorInfoResponseDto> authors,
        long totalElements,
        int totalPages,
        int currentPage,
        boolean hasNext
) {
    public static AuthorListResponseDto fromPage(Page<AuthorInfoResponseDto> authorPage) {
        return AuthorListResponseDto.builder()
                .authors(authorPage.getContent())
                .totalElements(authorPage.getTotalElements())
                .totalPages(authorPage.getTotalPages())
                .currentPage(authorPage.getNumber())
                .hasNext(authorPage.hasNext())
                .build();
    }
}