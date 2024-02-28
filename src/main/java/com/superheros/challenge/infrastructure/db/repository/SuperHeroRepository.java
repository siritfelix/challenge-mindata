package com.superheros.challenge.infrastructure.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superheros.challenge.infrastructure.db.entity.SuperHeroEntity;

public interface SuperHeroRepository extends JpaRepository<SuperHeroEntity, Integer> {
    public List<SuperHeroEntity> findByNameContaining(String nombre);
}
