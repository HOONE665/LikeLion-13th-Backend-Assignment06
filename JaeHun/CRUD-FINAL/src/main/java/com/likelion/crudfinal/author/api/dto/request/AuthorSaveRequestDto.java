package com.likelion.crudfinal.author.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthorSaveRequestDto(
        @NotBlank(message = "작가 이름은 필수입니다.")
        @Size(max = 100, message = "작가 이름은 100자를 초과할 수 없습니다.")
        String name,
        String biography
) {}