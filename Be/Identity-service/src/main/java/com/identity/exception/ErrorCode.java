package com.identity.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNKNOWN_ERROR(-1, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTHORIZATION_DENIED(9998,"Authorization denied" , HttpStatus.FORBIDDEN),
    INVALID_KEY(1 , "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(2, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(3, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(4, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(5, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(6, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(7, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(8, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(9, "Your password and confirmpassword must same", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

}
