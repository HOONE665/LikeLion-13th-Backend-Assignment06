package com.likelion.crudfinal.book.application;

import com.likelion.crudfinal.author.domain.Author;
import com.likelion.crudfinal.author.domain.repository.AuthorRepository;
import com.likelion.crudfinal.book.api.dto.request.BookSaveRequestDto;
import com.likelion.crudfinal.book.api.dto.request.BookUpdateRequestDto;
import com.likelion.crudfinal.book.api.dto.response.BookInfoResponseDto;
import com.likelion.crudfinal.book.domain.Book;
import com.likelion.crudfinal.book.domain.repository.BookRepository;
import com.likelion.crudfinal.common.error.ErrorCode; // 변경된 ErrorCode import
import com.likelion.crudfinal.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public BookInfoResponseDto saveBook(BookSaveRequestDto requestDto) {
        Author author = authorRepository.findById(requestDto.authorId())
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTHOR_NOT_FOUND, "책을 생성할 작가를 찾을 수 없습니다.")); // ErrorCode 변경

        Book book = Book.builder()
                .title(requestDto.title())
                .publicationYear(requestDto.publicationYear())
                .author(author)
                .build();
        return BookInfoResponseDto.from(bookRepository.save(book));
    }

    public BookInfoResponseDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND)); // ErrorCode 변경
        return BookInfoResponseDto.from(book);
    }

    public Page<BookInfoResponseDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(BookInfoResponseDto::from);
    }

    @Transactional
    public BookInfoResponseDto updateBook(Long bookId, BookUpdateRequestDto requestDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND)); // ErrorCode 변경

        Author newAuthor = null;
        if (requestDto.authorId() != null) {
            newAuthor = authorRepository.findById(requestDto.authorId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.AUTHOR_NOT_FOUND, "변경할 작가를 찾을 수 없습니다.")); // ErrorCode 변경
        }

        book.update(requestDto.title(), requestDto.publicationYear(), newAuthor);

        return BookInfoResponseDto.from(bookRepository.save(book));
    }

    @Transactional
    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BusinessException(ErrorCode.BOOK_NOT_FOUND); // ErrorCode 변경
        }
        bookRepository.deleteById(bookId);
    }
}