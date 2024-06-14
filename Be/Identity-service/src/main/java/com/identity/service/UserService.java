package com.identity.service;

import com.identity.config.CustomJwtDecoder;
import com.identity.constant.PredefinedRole;
import com.identity.dto.Request.UserCreateRequest;
import com.identity.dto.Response.UserResponse;
import com.identity.entity.Role;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.mapper.UserMapper;
import com.identity.repository.RoleRepository;
import com.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    CustomJwtDecoder customJwtDecoder;

    public UserResponse createUser(UserCreateRequest request) {
        log.info("Create user: ", request);
        User user = UserMapper.userCreateRequestToUser(request);

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        log.info("Create user: " + user);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return UserMapper.userToUserResponse(user);
    }

    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::userToUserResponse).toList();
    }

    public UserResponse getMyInfo() {
        return null;
    }

    public void deleteUser(String userEmail) {
        log.info("Delete user: ", userEmail);
        Optional<User> user = userRepository.findByEmail(userEmail);
        user.get().setEnabled(false);
        userRepository.save(user.get());
    }

    public UserResponse getUser(String email, String token) {

        var jwt = customJwtDecoder.decode(token);
        if (jwt.getSubject().equals(email)) {
            return UserMapper.userToUserResponse(userRepository.findByEmail(email)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        } else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
