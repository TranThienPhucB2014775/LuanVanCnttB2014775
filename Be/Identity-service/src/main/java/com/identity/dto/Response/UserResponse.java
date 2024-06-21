package com.identity.dto.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String email;
    String username;
    Boolean Enable;
    LocalDate dob;
    String city;
    String address;
    LocalDateTime createdDate;
    //    Set<RoleResponse> roles;
}
