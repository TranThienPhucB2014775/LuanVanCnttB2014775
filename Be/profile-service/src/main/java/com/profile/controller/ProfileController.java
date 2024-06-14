package com.profile.controller;

import com.profile.dto.ApiResponse;
import com.profile.dto.Request.ProfileCreationRequest;
import com.profile.dto.Request.ProfileUpdateRequest;
import com.profile.dto.Response.ProfileResponse;
import com.profile.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileController {

    ProfileService profileService;

    @PostMapping
    ApiResponse<ProfileResponse> createProfile(
            @RequestBody ProfileCreationRequest profileCreationRequest,
            @RequestHeader(value = "Authorization", defaultValue = "") String token) {
        log.info("1" + token);
        return ApiResponse.<ProfileResponse>builder()
                .code(0)
                .result(profileService.createProfile(profileCreationRequest, token.replace("Bearer ", "")))
                .build();
    }

    @PostMapping("/update")
    ApiResponse<ProfileResponse> updateProfile(
            @RequestBody ProfileUpdateRequest profileUpdateRequest,
            @RequestHeader(value = "Authorization", defaultValue = "") String token
    ) {
        log.info("2" + token);
        return ApiResponse.<ProfileResponse>builder()
                .code(0)
                .result(profileService.updateProfile(profileUpdateRequest, token.replace("Bearer ", "")))
                .build();
    }
}
