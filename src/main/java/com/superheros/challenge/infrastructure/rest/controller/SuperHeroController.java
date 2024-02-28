package com.superheros.challenge.infrastructure.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superheros.challenge.domain.service.SuperHeroServiceDomain;
import com.superheros.challenge.infrastructure.aspect.Time;
import com.superheros.challenge.infrastructure.rest.converter.SuperHeroConverter;
import com.superheros.challenge.infrastructure.rest.dto.ResponseDto;
import com.superheros.challenge.infrastructure.rest.dto.SuperHeroDto;
import com.superheros.challenge.shared.config.MenssageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Heroes", description = "Heroes Catalog")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = SuperHeroController.PATH)
public class SuperHeroController {
	public final static String PATH = "/heros";
	public final static String ALL = "/list";
	public final static String FILTER = "/filter";

	private final SuperHeroServiceDomain superHeroServiceDomain;
	private final MenssageResponse menssageResponse;

	@Time
	@Operation(summary = "Gets a list of superheroes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of superheroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHeroDto.class))))
	})
	@GetMapping(path = ALL)
	public ResponseEntity<List<SuperHeroDto>> findAll() {
		return ResponseEntity.ok(superHeroServiceDomain.findAll().stream().map(SuperHeroConverter::toSuperHeroDto)
				.collect(Collectors.toList()));
	}

	@Time
	@Operation(summary = "Gets a superhero for id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of superheroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHeroDto.class)))),
			@ApiResponse(responseCode = "404", description = "The superhero was not found", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
	})
	@GetMapping
	public ResponseEntity<SuperHeroDto> findById(@RequestParam Integer id) {
		return ResponseEntity.ok(SuperHeroConverter.toSuperHeroDto(superHeroServiceDomain.findById(id)));
	}

	@Time
	@Operation(summary = "Create the superhero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Create the superhero", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHeroDto.class)))),
			@ApiResponse(responseCode = "404", description = "The superhero was not found", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Errors in the request", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
	})
	@PostMapping
	public ResponseEntity<SuperHeroDto> create(@RequestBody SuperHeroDto superHeroDto) {
		return ResponseEntity.ok(SuperHeroConverter.toSuperHeroDto(superHeroServiceDomain
				.create(SuperHeroConverter.toSuperHero(superHeroDto))));
	}

	@Time
	@Operation(summary = "Update the superhero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Update the superhero", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHeroDto.class)))),
			@ApiResponse(responseCode = "404", description = "The superhero was not found", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Errors in the request", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
	})
	@PatchMapping
	public ResponseEntity<SuperHeroDto> upDate(@Valid @RequestBody SuperHeroDto superHeroDto) {
		return ResponseEntity.ok(SuperHeroConverter
				.toSuperHeroDto(superHeroServiceDomain
						.upDate(SuperHeroConverter.toSuperHero(superHeroDto))));
	}

	@Time
	@Operation(summary = "Delete superhero by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete superhero by id", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHeroDto.class)))),
			@ApiResponse(responseCode = "404", description = "The superhero was not found", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
	})
	@DeleteMapping
	public ResponseEntity<ResponseDto> removeById(@RequestParam Integer id) {
		superHeroServiceDomain.removeById(id);
		return ResponseEntity.ok(ResponseDto.builder()
				.code(MenssageResponse.OK)
				.message(menssageResponse.getMessages().get(MenssageResponse.OK))
				.build());
	}

	@Time
	@Operation(summary = "Get all superheroes by filter")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of superheroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHeroDto.class))))
	})
	@GetMapping(path = FILTER)
	public ResponseEntity<List<SuperHeroDto>> findByAllByFilter(@RequestParam String name) {
		return ResponseEntity.ok(superHeroServiceDomain.findByAllByFilter(name).stream()
				.map(SuperHeroConverter::toSuperHeroDto)
				.collect(Collectors.toList()));
	}
}
