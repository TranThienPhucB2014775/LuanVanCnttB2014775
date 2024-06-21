package com.identity.mapper;

import com.identity.dto.Request.UserCreateRequest;
import com.identity.dto.Response.UserResponse;
import com.identity.entity.User;

public class UserMapper {
    public static User userCreateRequestToUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());
        user.setEmail(userCreateRequest.getEmail());
        return user;
    }

    public static UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .createdDate(user.getCreatedAt())
                .Enable(user.getEnabled())
                .build();
        //        UserResponse userResponse = new UserResponse();
        //        userResponse.setUsername(user.getUsername());
        //        userResponse.setEmail(user.getEmail());
        //        userResponse.setRoles(RoleMapper.map(user.getRoles()));
        //        userResponse.setEnable(user.getEnabled());
        //        userResponse.setEnable(user.getEnabled());
        //        return userResponse;
    }
}
