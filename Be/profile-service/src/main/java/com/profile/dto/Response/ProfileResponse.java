package com.profile.dto.Response;

import com.profile.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProfileResponse {
    LocalDate dob;
    String city;
    String address;

}
