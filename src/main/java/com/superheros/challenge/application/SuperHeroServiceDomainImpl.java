package com.superheros.challenge.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.superheros.challenge.domain.SuperHero;
import com.superheros.challenge.domain.service.SuperHeroServiceDomain;
import com.superheros.challenge.infrastructure.db.entity.SuperHeroEntity;
import com.superheros.challenge.infrastructure.db.entity.SuperHeroSkillEntity;
import com.superheros.challenge.infrastructure.db.repository.SuperHeroRepository;
import com.superheros.challenge.infrastructure.db.repository.SuperHeroSkillRepository;
import com.superheros.challenge.infrastructure.rest.converter.SuperHeroConverter;
import com.superheros.challenge.infrastructure.rest.dto.ResponseDto;
import com.superheros.challenge.shared.config.MenssageResponse;
import com.superheros.challenge.shared.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SuperHeroServiceDomainImpl implements SuperHeroServiceDomain {
    private final SuperHeroRepository superHeroRepository;
    private final SuperHeroSkillRepository superHeroSkillRepository;
    private final MenssageResponse menssageResponse;

    @Override
    public List<SuperHero> findAll() {
        return superHeroRepository.findAll().stream().map(SuperHeroConverter::toSuperHero).collect(Collectors.toList());
    }

    @Override
    public SuperHero findById(Integer id) {
        return SuperHeroConverter.toSuperHero(this.getById(id));
    }

    @Override
    public SuperHero create(SuperHero superHero) {
        return SuperHeroConverter
                .toSuperHero(superHeroRepository.save(SuperHeroConverter.toSuperHeroEntity(superHero)));
    }

    @Override
    public SuperHero upDate(SuperHero superHero) {
        SuperHeroEntity superHeroEntity = this.getById(superHero.getId());
        List<SuperHeroSkillEntity> superHeroSkillEntities = superHeroEntity.getSkills();
        superHeroEntity.setName(superHero.getName());
        superHeroEntity.setSkills(new ArrayList<>());
        superHero.getSkills().forEach(skill -> superHeroEntity.addSkill(skill));
        SuperHeroEntity reponse = superHeroRepository.save(superHeroEntity);
        superHeroSkillRepository.deleteAll(superHeroSkillEntities);
        return SuperHeroConverter.toSuperHero(reponse);

    }

    @Override
    public void removeById(Integer id) {
        SuperHeroEntity superHeroEntity = this.getById(id);
        superHeroRepository.delete(superHeroEntity);
    }

    @Override
    public List<SuperHero> findByAllByFilter(String name) {
        return superHeroRepository.findByNameContaining(name).stream().map(SuperHeroConverter::toSuperHero)
                .collect(Collectors.toList());

    }

    public SuperHeroEntity getById(Integer id) {
        return superHeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ResponseDto.builder()
                        .code(MenssageResponse.F401)
                        .message(menssageResponse.getMessages().get(MenssageResponse.F401)
                                .concat(id.toString()))
                        .build()));
    }
}
