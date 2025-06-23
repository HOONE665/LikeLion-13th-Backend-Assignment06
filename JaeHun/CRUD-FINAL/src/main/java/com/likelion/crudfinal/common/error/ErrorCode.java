package com.likelion.crudfinal.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "BAD_REQUEST_400"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사에 실패했습니다.", "VALIDATION_400"),
    ALREADY_EXISTS_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.", "ALREADY_EXISTS_EMAIL_409"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.", "INVALID_PASSWORD_400"),

    AUTHOR_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작가를 찾을 수 없습니다.", "AUTHOR_NOT_FOUND_404"),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 책을 찾을 수 없습니다.", "BOOK_NOT_FOUND_404"),
    MEMBER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 사용자가 없습니다.", "MEMBER_NOT_FOUND_404"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다.", "INTERNAL_SERVER_ERROR_500");

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}