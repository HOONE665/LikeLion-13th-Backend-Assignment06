package com.likelion.crudfinal.book.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record BookUpdateRequestDto(
        @Nullable
        @Size(max = 200, message = "책 제목은 200자를 초과할 수 없습니다.")
        String title,

        @Nullable
        @Min(value = 1000, message = "출판 연도는 최소 1000년이어야 합니다.")
        Integer publicationYear,

        @Nullable
        Long authorId
) {}