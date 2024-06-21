package com.identity.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.identity.dto.ApiResponse;
import feign.FeignException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.identity.config.CustomJwtDecoder;
import com.identity.constant.PredefinedRole;
import com.identity.dto.Request.ProfileCreationRequest;
import com.identity.dto.Request.UserCreateRequest;
import com.identity.dto.Response.UserResponse;
import com.identity.entity.Role;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.mapper.ProfileMapper;
import com.identity.mapper.UserMapper;
import com.identity.repository.RoleRepository;
import com.identity.repository.UserRepository;
import com.identity.service.client.ProfileClientService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    CustomJwtDecoder customJwtDecoder;
    ProfileClientService profileClientService;

    public UserResponse createUser(UserCreateRequest request) {
        User user = UserMapper.userCreateRequestToUser(request);

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        log.info("Create user: {}", user);

        ProfileCreationRequest profileCreationRequest =
                ProfileMapper.userCreateRequestToProfileCreationRequest(request);

        var profileCreation = profileClientService.createProfile(profileCreationRequest);

        user.setProfileId(profileCreation.getResult().getProfileId());
        user = userRepository.save(user);

        UserResponse userResponse = UserMapper.userToUserResponse(user);

        ProfileMapper.profileCreationResponseOnUserResponse(userResponse, profileCreation.getResult());

        return userResponse;

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::userToUserResponse).toList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(String userEmail) {
        log.info("Delete user: ", userEmail);
        Optional<User> user = userRepository.findByEmail(userEmail);
        user.get().setEnabled(false);
        userRepository.save(user.get());
    }

    public UserResponse getUser(String token) {
        var jwt = customJwtDecoder.decode(token);

        var user =
                userRepository.findByEmail(jwt.getSubject()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        log.info("Get user: " + user.getProfileId());

        try {
            var profileResponse =
                    profileClientService.getProfile(user.getProfileId(), "Bearer " + token);
            UserResponse userResponse = UserMapper.userToUserResponse(user);

            ProfileMapper.profileResponseOnUserResponse(userResponse, profileResponse.getResult());

            return userResponse;

        } catch (FeignException.Unauthorized e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

    }
}
