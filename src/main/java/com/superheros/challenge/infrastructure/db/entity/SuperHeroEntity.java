package com.superheros.challenge.infrastructure.db.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "superheroes")
public class SuperHeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "superHero", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SuperHeroSkillEntity> skills;

    public void addSkill(String skill) {
        if (Objects.isNull(skills)) {
            skills = new ArrayList<>();
        }
        if (!skills.stream().anyMatch(skillEntity -> skill.equals(skillEntity.getDescription()))) {
            skills.add(SuperHeroSkillEntity.builder().superHero(this).description(skill).build());
        }
    }
}
