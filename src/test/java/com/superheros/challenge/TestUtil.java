package com.superheros.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superheros.challenge.infrastructure.db.entity.SuperHeroEntity;
import com.superheros.challenge.infrastructure.db.entity.SuperHeroSkillEntity;
import com.superheros.challenge.infrastructure.rest.dto.SignUpRequest;
import com.superheros.challenge.infrastructure.rest.dto.SuperHeroDto;
import com.superheros.challenge.shared.config.MenssageResponse;

public class TestUtil {
    public static final Integer ID_1 = 1;
    public static final Integer ID_2 = 2;
    public static final Integer ID_3 = 3;
    public static final Integer ID_11 = 11;
    public static final String EMAIL_1 = "email1@email.com";
    public static final String EMAIL_2 = "email2@email.com";
    public static final String EMAIL_11 = "email11@email.com";
    public static final String EMAIL_TOKEN = "siritfelix@gmail.com";
    public static final String PASSWORD = "12345";

    public static final double PRICE_PRODUCT = 10;

    public static Map<String, String> buidlMenssageResponse() {
        Map<String, String> messages = new HashMap<>();
        messages.put(MenssageResponse.SP, "");
        messages.put(MenssageResponse.OK, "");
        messages.put(MenssageResponse.BR400, "");
        messages.put(MenssageResponse.F401, "");
        messages.put(MenssageResponse.E500, "");
        messages.put(MenssageResponse.E403, "");
        messages.put(MenssageResponse.E409, "");
        return messages;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<SuperHeroEntity> buildSuperHeroEntityList(Integer n) {
        List<SuperHeroEntity> superHeroEntities = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            superHeroEntities.add(buildSuperHeroEntityOptional(i, i).get());
        }
        return superHeroEntities;

    }

    public static Optional<SuperHeroEntity> buildSuperHeroEntityOptional(Integer n, Integer start) {
        return Optional.of(SuperHeroEntity.builder()
                .id(n)
                .name("name" + n.toString())
                .skills(buildSuperHeroSkillEntityList(n, start))
                .build());
    }

    public static List<SuperHeroSkillEntity> buildSuperHeroSkillEntityList(Integer n, Integer start) {
        List<SuperHeroSkillEntity> superHeroSkillEntities = new ArrayList<>();

        for (int i = start; i <= n; i++) {
            superHeroSkillEntities.add(buildSuperHeroSkillEntityOptional(i).get());
        }
        return superHeroSkillEntities;
    }

    public static Optional<SuperHeroSkillEntity> buildSuperHeroSkillEntityOptional(Integer id) {
        return Optional.of(SuperHeroSkillEntity.builder()
                .id(id)
                .description("description" + id.toString())
                .build());
    }

    public static SignUpRequest buidlSignUpRequest(String email) {
        return SignUpRequest.builder().firstName("name").lastName("lastname").password(PASSWORD)
                .email(email).password(PASSWORD)
                .build();
    }

    public static SignUpRequest buidlSignInRequest() {
        return SignUpRequest.builder().email(EMAIL_TOKEN).password(PASSWORD)
                .build();
    }
    

    public static SuperHeroDto buildSuperHeroDto() {
        return new SuperHeroDto(null, EMAIL_1, buildSkillsDto());
    }

    public static SuperHeroDto buildSuperHeroDto(Integer id) {
        return new SuperHeroDto(id, EMAIL_2, buildSkillsDto());
    }

    public static List<String> buildSkillsDto() {
        return Arrays.asList("Fuerza", "Valocidad");
    }

}
