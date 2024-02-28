package com.superheros.challenge.application;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.superheros.challenge.domain.service.AuthenticationService;
import com.superheros.challenge.domain.service.JwtService;
import com.superheros.challenge.infrastructure.db.entity.Role;
import com.superheros.challenge.infrastructure.db.entity.User;
import com.superheros.challenge.infrastructure.db.repository.UserRepository;
import com.superheros.challenge.infrastructure.rest.dto.JwtAuthenticationResponse;
import com.superheros.challenge.infrastructure.rest.dto.ResponseDto;
import com.superheros.challenge.infrastructure.rest.dto.SignUpRequest;
import com.superheros.challenge.infrastructure.rest.dto.SigninRequest;
import com.superheros.challenge.shared.config.MenssageResponse;
import com.superheros.challenge.shared.exception.ConflictException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final MenssageResponse menssageResponse;

	@Override
	public ResponseDto signup(SignUpRequest request) {
		var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER).build();
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new ConflictException(ResponseDto.builder()
					.code(MenssageResponse.E409)
					.message(menssageResponse.getMessages().get(MenssageResponse.E409)
							.concat(request.getEmail()))
					.build());
		}
		userRepository.save(user);
		return ResponseDto.builder()
				.code(MenssageResponse.OK)
				.message(menssageResponse.getMessages().get(MenssageResponse.OK))
				.build();
	}

	@Override
	public JwtAuthenticationResponse signin(SigninRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
		var jwt = jwtService.generateToken(user);
		return JwtAuthenticationResponse.builder().token(jwt).build();
	}
}
