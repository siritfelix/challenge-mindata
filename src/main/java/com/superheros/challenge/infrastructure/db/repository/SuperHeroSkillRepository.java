package com.superheros.challenge.infrastructure.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superheros.challenge.infrastructure.db.entity.SuperHeroSkillEntity;

public interface SuperHeroSkillRepository extends JpaRepository<SuperHeroSkillEntity, Integer> {

}
