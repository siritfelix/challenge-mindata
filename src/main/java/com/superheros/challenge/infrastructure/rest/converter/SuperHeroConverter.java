package com.superheros.challenge.infrastructure.rest.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.superheros.challenge.domain.SuperHero;
import com.superheros.challenge.infrastructure.db.entity.SuperHeroEntity;
import com.superheros.challenge.infrastructure.db.entity.SuperHeroSkillEntity;
import com.superheros.challenge.infrastructure.rest.dto.SuperHeroDto;

public class SuperHeroConverter {

    public static SuperHeroDto toSuperHeroDto(SuperHero superHero) {
        return new SuperHeroDto(superHero.getId(), superHero.getName(), superHero.getSkills());
    }

    public static SuperHero toSuperHero(SuperHeroDto superHeroDto) {
        return new SuperHero(superHeroDto.id(), superHeroDto.name(), superHeroDto.skills());
    }

    public static SuperHero toSuperHero(SuperHeroEntity superHeroeEntity) {
        return new SuperHero(superHeroeEntity.getId(), superHeroeEntity.getName(), superHeroeEntity.getSkills().stream()
                .map(SuperHeroSkillEntity::getDescription).collect(Collectors.toList()));
    }

    public static SuperHeroEntity toSuperHeroEntity(SuperHero superHeroe) {
        SuperHeroEntity superHeroEntity = new SuperHeroEntity(null, superHeroe.getName(), null);
        List<SuperHeroSkillEntity> superHeroSkillEntities = superHeroe.getSkills().stream()
                .map(skill -> new SuperHeroSkillEntity(null, skill, superHeroEntity)).collect(Collectors.toList());
        superHeroEntity.setSkills(superHeroSkillEntities);
        return superHeroEntity;
    }

}
