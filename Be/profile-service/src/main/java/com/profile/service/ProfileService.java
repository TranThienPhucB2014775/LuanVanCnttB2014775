package com.profile.service;

import com.profile.config.CustomJwtDecoder;
import com.profile.dto.Request.ProfileCreationRequest;
import com.profile.dto.Request.ProfileUpdateRequest;
import com.profile.dto.Response.ProfileResponse;
import com.profile.entity.Profile;
import com.profile.exception.AppException;
import com.profile.exception.ErrorCode;
import com.profile.mapper.ProfileMapper;
import com.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileService {

    ProfileRepository profileRepository;
    CustomJwtDecoder customJwtDecoder;

    @PreAuthorize("hasRole('USER')")
    public ProfileResponse createProfile(ProfileCreationRequest profileRequest, String token) {
        Profile profile = ProfileMapper.ProfileCreationRequestToProfile(profileRequest);
        var jwt = customJwtDecoder.decode(token);
        try {
            return ProfileMapper.ProfileToProfileResponse(profileRepository.save(profile));
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @PreAuthorize("hasRole('USER')")
    public ProfileResponse updateProfile(ProfileUpdateRequest profileUpdateRequest, String token) {
        var jwt = customJwtDecoder.decode(token);
        log.info(jwt.getSubject());
        log.info(profileUpdateRequest.getUserId());
        Profile profile = ProfileMapper.ProfileUpdateRequestToProfile(profileUpdateRequest);
        log.info(profile.toString());
        return ProfileMapper.ProfileToProfileResponse(profileRepository.save(profile));

    }


}
