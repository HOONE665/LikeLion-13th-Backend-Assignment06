package com.likelion.crudfinal.book.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record BookSaveRequestDto(
        @NotBlank(message = "책 제목은 필수입니다.")
        @Size(max = 200, message = "책 제목은 200자를 초과할 수 없습니다.")
        String title,

        @NotNull(message = "출판 연도는 필수입니다.")
        @Min(value = 1000, message = "출판 연도는 최소 1000년이어야 합니다.")
        Integer publicationYear,

        @NotNull(message = "작가 ID는 필수입니다.")
        Long authorId
) {}