package com.superheros.challenge.infrastructure.rest.dto;

import lombok.Builder;

@Builder
public record ResponseDto(String code, String message) {

}
