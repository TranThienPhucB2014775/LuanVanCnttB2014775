package com.profile.mapper;

import com.profile.dto.Request.ProfileCreationRequest;
import com.profile.dto.Request.ProfileUpdateRequest;
import com.profile.dto.Response.ProfileResponse;
import com.profile.entity.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileMapper {
    private static final Logger log = LoggerFactory.getLogger(ProfileMapper.class);

    public static ProfileResponse ProfileToProfileResponse(Profile profile) {
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setAddress(profile.getAddress());
        profileResponse.setCity(profile.getCity());
        profileResponse.setDob(profile.getDob());
        return profileResponse;
    }

    public static Profile ProfileCreationRequestToProfile(ProfileCreationRequest profileRequest) {
        Profile profile = new Profile();
        profile.setAddress(profileRequest.getAddress());
        profile.setCity(profileRequest.getCity());
        profile.setDob(profileRequest.getDob());
        profile.setUserId(profileRequest.getUserId());
        return profile;
    }

    public static Profile ProfileUpdateRequestToProfile(ProfileUpdateRequest profileRequest) {
        Profile profile = new Profile();
        profile.setId(profileRequest.getId());
        profile.setAddress(profileRequest.getAddress());
        profile.setCity(profileRequest.getCity());
        profile.setDob(profileRequest.getDob());
        return profile;
    }


}
