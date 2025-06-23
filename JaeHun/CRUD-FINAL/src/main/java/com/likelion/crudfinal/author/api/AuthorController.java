package com.likelion.crudfinal.author.api;

import com.likelion.crudfinal.author.api.dto.request.AuthorSaveRequestDto;
import com.likelion.crudfinal.author.api.dto.request.AuthorUpdateRequestDto;
import com.likelion.crudfinal.author.api.dto.response.AuthorInfoResponseDto;
import com.likelion.crudfinal.author.api.dto.response.AuthorListResponseDto;
import com.likelion.crudfinal.author.application.AuthorService;
import com.likelion.crudfinal.common.error.SuccessCode;
import com.likelion.crudfinal.common.template.ApiResTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors") // /api/v1 제거
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResTemplate<AuthorInfoResponseDto>> saveAuthor(@Valid @RequestBody AuthorSaveRequestDto requestDto) {
        AuthorInfoResponseDto savedAuthor = authorService.saveAuthor(requestDto);
        return ResponseEntity.status(SuccessCode.AUTHOR_CREATED_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successResponse(SuccessCode.AUTHOR_CREATED_SUCCESS, savedAuthor));
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<ApiResTemplate<AuthorInfoResponseDto>> getAuthorById(@PathVariable Long authorId) {
        AuthorInfoResponseDto authorInfo = authorService.getAuthorById(authorId);
        return ResponseEntity.status(SuccessCode.AUTHOR_FETCH_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successResponse(SuccessCode.AUTHOR_FETCH_SUCCESS, authorInfo));
    }

    @GetMapping
    public ResponseEntity<ApiResTemplate<AuthorListResponseDto>> getAllAuthors(Pageable pageable) {
        AuthorListResponseDto authorList = AuthorListResponseDto.fromPage(authorService.getAllAuthors(pageable));
        return ResponseEntity.status(SuccessCode.AUTHOR_FETCH_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successResponse(SuccessCode.AUTHOR_FETCH_SUCCESS, authorList));
    }

    @PatchMapping("/{authorId}")
    public ResponseEntity<ApiResTemplate<AuthorInfoResponseDto>> updateAuthor(@PathVariable Long authorId,
                                                                              @Valid @RequestBody AuthorUpdateRequestDto requestDto) {
        AuthorInfoResponseDto updatedAuthor = authorService.updateAuthor(authorId, requestDto);
        return ResponseEntity.status(SuccessCode.AUTHOR_UPDATED_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successResponse(SuccessCode.AUTHOR_UPDATED_SUCCESS, updatedAuthor));
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<ApiResTemplate<Void>> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.status(SuccessCode.AUTHOR_DELETED_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successWithNoContent(SuccessCode.AUTHOR_DELETED_SUCCESS));
    }
}