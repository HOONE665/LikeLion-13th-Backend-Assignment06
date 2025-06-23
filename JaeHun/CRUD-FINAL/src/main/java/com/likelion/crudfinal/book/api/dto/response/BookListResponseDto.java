package com.likelion.crudfinal.book.api.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;
import java.util.List;

@Builder
public record BookListResponseDto(
        List<BookInfoResponseDto> books,
        long totalElements,
        int totalPages,
        int currentPage,
        boolean hasNext
) {
    public static BookListResponseDto fromPage(Page<BookInfoResponseDto> bookPage) {
        return BookListResponseDto.builder()
                .books(bookPage.getContent())
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .currentPage(bookPage.getNumber())
                .hasNext(bookPage.hasNext())
                .build();
    }
}