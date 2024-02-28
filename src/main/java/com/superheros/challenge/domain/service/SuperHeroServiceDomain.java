package com.superheros.challenge.domain.service;

import java.util.List;

import com.superheros.challenge.domain.SuperHero;

public interface SuperHeroServiceDomain {

    public List<SuperHero> findAll();

    public SuperHero findById(Integer id);

    public SuperHero create(SuperHero superHero);

    public SuperHero upDate(SuperHero superHero);

    public void removeById(Integer id);

    public List<SuperHero> findByAllByFilter(String name);
}
