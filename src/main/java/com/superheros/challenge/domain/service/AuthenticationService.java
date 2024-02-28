package com.superheros.challenge.domain.service;

import com.superheros.challenge.infrastructure.rest.dto.JwtAuthenticationResponse;
import com.superheros.challenge.infrastructure.rest.dto.ResponseDto;
import com.superheros.challenge.infrastructure.rest.dto.SignUpRequest;
import com.superheros.challenge.infrastructure.rest.dto.SigninRequest;

public interface AuthenticationService {
    ResponseDto signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
