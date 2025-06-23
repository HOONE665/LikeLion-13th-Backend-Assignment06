package com.likelion.crudfinal.common.template;

import com.likelion.crudfinal.common.error.ErrorCode;
import com.likelion.crudfinal.common.error.SuccessCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResTemplate<T> {

    private final int code;
    private final String message;
    private T data;

    public static ApiResTemplate<Void> successWithNoContent(SuccessCode successCode) {
        return new ApiResTemplate<>(successCode.getHttpStatusCode(), successCode.getMessage());
    }

    public static <T> ApiResTemplate<T> successResponse(SuccessCode successCode, T data) {
        return new ApiResTemplate<>(successCode.getHttpStatusCode(), successCode.getMessage(), data);
    }

    public static <T> ApiResTemplate<T> errorResponse(ErrorCode errorCode, T data) {
        return new ApiResTemplate<>(errorCode.getHttpStatusCode(), errorCode.getMessage(), data);
    }

    public static ApiResTemplate<Void> errorResponse(ErrorCode errorCode, String customMessage) {
        return new ApiResTemplate<>(errorCode.getHttpStatusCode(), customMessage);
    }
}