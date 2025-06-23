package com.likelion.crudfinal.common.exception;

import com.likelion.crudfinal.common.error.ErrorCode;
import com.likelion.crudfinal.common.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResTemplate<Void> handleServerException(final Exception e) {
        return ApiResTemplate.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResTemplate<Void>> handleCustomException(BusinessException e) {
        ApiResTemplate<Void> body = ApiResTemplate.errorResponse(e.getErrorCode(), e.getCustomMessage());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(body);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResTemplate<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ApiResTemplate.errorResponse(ErrorCode.VALIDATION_ERROR, errorMap);
    }
}