package com.superheros.challenge.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SuperHero {
    private Integer id;
    private String name;
    private List<String> skills;
}
