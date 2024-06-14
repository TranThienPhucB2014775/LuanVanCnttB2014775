package com.profile.dto.Request;

import com.profile.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProfileCreationRequest {

    @Size(min = 32, message = "INVALID_USER_ID")
    String userId;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    @Size(min = 32, message = "INVALID_USER_ID")
    String city;

    String address;
}
