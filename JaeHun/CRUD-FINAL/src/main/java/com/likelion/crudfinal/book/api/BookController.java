package com.likelion.crudfinal.book.api;

import com.likelion.crudfinal.book.api.dto.request.BookSaveRequestDto;
import com.likelion.crudfinal.book.api.dto.request.BookUpdateRequestDto;
import com.likelion.crudfinal.book.api.dto.response.BookInfoResponseDto;
import com.likelion.crudfinal.book.api.dto.response.BookListResponseDto;
import com.likelion.crudfinal.book.application.BookService;
import com.likelion.crudfinal.common.error.SuccessCode;
import com.likelion.crudfinal.common.template.ApiResTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books") // /api/v1 제거
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResTemplate<BookInfoResponseDto>> saveBook(@Valid @RequestBody BookSaveRequestDto requestDto) {
        BookInfoResponseDto savedBook = bookService.saveBook(requestDto);
        return ResponseEntity.status(SuccessCode.BOOK_CREATED_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successResponse(SuccessCode.BOOK_CREATED_SUCCESS, savedBook));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResTemplate<BookInfoResponseDto>> getBookById(@PathVariable Long bookId) {
        BookInfoResponseDto bookInfo = bookService.getBookById(bookId);
        return ResponseEntity.status(SuccessCode.BOOK_FETCH_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successResponse(SuccessCode.BOOK_FETCH_SUCCESS, bookInfo));
    }

    @GetMapping
    public ResponseEntity<ApiResTemplate<BookListResponseDto>> getAllBooks(Pageable pageable) {
        BookListResponseDto bookList = BookListResponseDto.fromPage(bookService.getAllBooks(pageable));
        return ResponseEntity.status(SuccessCode.BOOK_FETCH_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successResponse(SuccessCode.BOOK_FETCH_SUCCESS, bookList));
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<ApiResTemplate<BookInfoResponseDto>> updateBook(@PathVariable Long bookId,
                                                                          @Valid @RequestBody BookUpdateRequestDto requestDto) {
        BookInfoResponseDto updatedBook = bookService.updateBook(bookId, requestDto);
        return ResponseEntity.status(SuccessCode.BOOK_UPDATED_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successResponse(SuccessCode.BOOK_UPDATED_SUCCESS, updatedBook));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResTemplate<Void>> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(SuccessCode.BOOK_DELETED_SUCCESS.getHttpStatus())
                .body(ApiResTemplate.successWithNoContent(SuccessCode.BOOK_DELETED_SUCCESS));
    }
}