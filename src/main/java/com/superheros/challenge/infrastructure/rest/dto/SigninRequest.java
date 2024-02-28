package com.superheros.challenge.infrastructure.rest.dto;

import com.superheros.challenge.shared.config.MenssageResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

    @Schema(description = "Email, Unique identifier,", example = "siritfelix@gmail.com", required = true)
    @Email(message = MenssageResponse.BR400)
    @NotBlank(message = MenssageResponse.BR400)
    private String email;

    @Schema(description = "password,", example = "12345", required = true)
    @NotBlank(message = MenssageResponse.BR400)
    private String password;
}
