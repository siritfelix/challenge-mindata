package com.superheros.challenge.infrastructure.rest.dto;

import java.util.List;

import com.superheros.challenge.shared.config.MenssageResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SuperHeroDto(
                @Schema(description = "Unique identifier of the superhero,", example = "1", required = false) @Valid @NotNull(message = MenssageResponse.BR400) Integer id,
                @Schema(description = "Superhero name,", example = "iroman", required = true) String name,
                @Schema(description = "Superhero skills list,", required = false) List<String> skills) {
}
