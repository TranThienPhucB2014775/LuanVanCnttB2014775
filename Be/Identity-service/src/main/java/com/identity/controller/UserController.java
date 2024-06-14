package com.identity.controller;


import com.identity.dto.ApiResponse;
import com.identity.dto.Request.UserCreateRequest;
import com.identity.dto.Response.UserResponse;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;

    @PostMapping("/registration")
    ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {

        if (!request.getPassword().equals(request.getConfirmpassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder()
                        .result(userService.createUser(request))
                        .build());
    }

    @GetMapping("/list-user")
    ApiResponse<List<UserResponse>> getAllUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();

    }

    @GetMapping("/{userEmail}")
    ApiResponse<UserResponse> getAllUser(
            @PathVariable String userEmail,
            @RequestHeader(value = "Token", defaultValue = "") String token) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userEmail, token.replace("Bearer ", "")))
                .build();

    }

    @DeleteMapping("/{userEmail}")
    public ApiResponse<String> deleteUser(@PathVariable String userEmail) {
        log.info("Delete user with email {}", userEmail);
        userService.deleteUser(userEmail);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
