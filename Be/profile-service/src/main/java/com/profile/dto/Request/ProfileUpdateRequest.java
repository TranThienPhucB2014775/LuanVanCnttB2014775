package com.profile.dto.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.profile.validator.DobConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequest {
    String profileId;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    @Size(min = 32, message = "INVALID_USER_ID")
    String city;

    String address;
}
