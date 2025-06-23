package com.likelion.crudfinal.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "리소스를 성공적으로 생성했습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "성공적으로 처리되었으나, 응답할 콘텐츠가 없습니다."),

    AUTHOR_CREATED_SUCCESS(HttpStatus.CREATED, "작가를 성공적으로 생성했습니다."),
    AUTHOR_FETCH_SUCCESS(HttpStatus.OK, "작가 정보를 성공적으로 조회했습니다."),
    AUTHOR_UPDATED_SUCCESS(HttpStatus.OK, "작가 정보를 성공적으로 수정했습니다."),
    AUTHOR_DELETED_SUCCESS(HttpStatus.NO_CONTENT, "작가를 성공적으로 삭제했습니다."),

    BOOK_CREATED_SUCCESS(HttpStatus.CREATED, "책을 성공적으로 생성했습니다."),
    BOOK_FETCH_SUCCESS(HttpStatus.OK, "책 정보를 성공적으로 조회했습니다."),
    BOOK_UPDATED_SUCCESS(HttpStatus.OK, "책 정보를 성공적으로 수정했습니다."),
    BOOK_DELETED_SUCCESS(HttpStatus.NO_CONTENT, "책을 성공적으로 삭제했습니다."),

    MEMBER_CREATED_SUCCESS(HttpStatus.CREATED, "사용자가 성공적으로 생성되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}