package com.profile.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.profile.config.CustomJwtDecoder;
import com.profile.dto.Request.ProfileCreationRequest;
import com.profile.dto.Request.ProfileUpdateRequest;
import com.profile.dto.Response.ProfileResponse;
import com.profile.entity.Profile;
import com.profile.exception.AppException;
import com.profile.exception.ErrorCode;
import com.profile.mapper.ProfileMapper;
import com.profile.repository.ProfileRepository;
import com.profile.service.client.UserClientService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileService {

    ProfileRepository profileRepository;
    CustomJwtDecoder customJwtDecoder;
    UserClientService userClientService;

    public ProfileResponse createProfile(ProfileCreationRequest profileRequest) {
        Profile profile = ProfileMapper.ProfileCreationRequestToProfile(profileRequest);
        try {
            return ProfileMapper.ProfileToProfileResponse(profileRepository.save(profile));
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public ProfileResponse updateProfile(ProfileUpdateRequest profileUpdateRequest, String token) {
        var jwt = customJwtDecoder.decode(token);
        Profile profile = ProfileMapper.ProfileUpdateRequestToProfile(profileUpdateRequest);
        log.info(profile.toString());
        return ProfileMapper.ProfileToProfileResponse(profileRepository.save(profile));
    }

    public ProfileResponse getProfile(String id) {

        return ProfileMapper.ProfileToProfileResponse(
                profileRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_FOUND)));
    }
}
