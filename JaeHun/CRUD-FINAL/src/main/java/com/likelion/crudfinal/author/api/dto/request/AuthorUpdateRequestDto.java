package com.likelion.crudfinal.author.api.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record AuthorUpdateRequestDto(
        @Nullable
        @Size(max = 100, message = "작가 이름은 100자를 초과할 수 없습니다.")
        String name,
        @Nullable
        String biography
) {}