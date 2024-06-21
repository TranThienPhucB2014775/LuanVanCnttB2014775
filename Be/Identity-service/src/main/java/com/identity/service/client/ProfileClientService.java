package com.identity.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.identity.dto.ApiResponse;
import com.identity.dto.Request.ProfileCreationRequest;
import com.identity.dto.Response.ProfileCreationResponse;
import com.identity.dto.Response.ProfileResponse;

@FeignClient(name = "profile-service")
public interface ProfileClientService {
    @PostMapping(value = "/profile", consumes = "application/json")
    ApiResponse<ProfileCreationResponse> createProfile(@RequestBody ProfileCreationRequest profileCreationRequest);

    @GetMapping(value = "/profile/{profileId}", consumes = "application/json")
    ApiResponse<ProfileResponse> getProfile(@PathVariable String profileId, @RequestHeader String Authorization);
}
