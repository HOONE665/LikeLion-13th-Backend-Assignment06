package com.likelion.crudfinal.author.application;

import com.likelion.crudfinal.author.api.dto.request.AuthorSaveRequestDto;
import com.likelion.crudfinal.author.api.dto.request.AuthorUpdateRequestDto;
import com.likelion.crudfinal.author.api.dto.response.AuthorInfoResponseDto;
import com.likelion.crudfinal.author.domain.Author;
import com.likelion.crudfinal.author.domain.repository.AuthorRepository;
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
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorInfoResponseDto saveAuthor(AuthorSaveRequestDto requestDto) {
        Author author = Author.builder()
                .name(requestDto.name())
                .biography(requestDto.biography())
                .build();
        return AuthorInfoResponseDto.from(authorRepository.save(author));
    }

    public AuthorInfoResponseDto getAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTHOR_NOT_FOUND)); // ErrorCode 변경
        return AuthorInfoResponseDto.from(author);
    }

    public Page<AuthorInfoResponseDto> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(AuthorInfoResponseDto::from);
    }

    @Transactional
    public AuthorInfoResponseDto updateAuthor(Long authorId, AuthorUpdateRequestDto requestDto) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTHOR_NOT_FOUND)); // ErrorCode 변경

        author.update(requestDto.name(), requestDto.biography());

        return AuthorInfoResponseDto.from(authorRepository.save(author));
    }

    @Transactional
    public void deleteAuthor(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new BusinessException(ErrorCode.AUTHOR_NOT_FOUND); // ErrorCode 변경
        }
        authorRepository.deleteById(authorId);
    }
}