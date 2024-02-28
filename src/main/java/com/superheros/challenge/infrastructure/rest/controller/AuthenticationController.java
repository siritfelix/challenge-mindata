package com.superheros.challenge.infrastructure.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superheros.challenge.domain.service.AuthenticationService;
import com.superheros.challenge.infrastructure.rest.dto.JwtAuthenticationResponse;
import com.superheros.challenge.infrastructure.rest.dto.ResponseDto;
import com.superheros.challenge.infrastructure.rest.dto.SignUpRequest;
import com.superheros.challenge.infrastructure.rest.dto.SigninRequest;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@Tag(name = "Authentication", description = "Authentication catalog")
@RestController
@RequestMapping(path = AuthenticationController.PATH)
@RequiredArgsConstructor
public class AuthenticationController {
    public static final String PATH = "/api/v1/auth/";
    public static final String SINGUP = "signup";
    public static final String SINGIN = "signin";

    private final AuthenticationService authenticationService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDto.class)))),
            @ApiResponse(responseCode = "403", description = "User already exists", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping(SINGUP)
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticate user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = JwtAuthenticationResponse.class)))),
            @ApiResponse(responseCode = "409", description = "Authenticate fail", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping(SINGIN)
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
