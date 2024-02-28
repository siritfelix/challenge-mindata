package com.superheros.challenge.infrastructure.db.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "superheroskills")
public class SuperHeroSkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private SuperHeroEntity superHero;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            String description = ((SuperHeroSkillEntity) obj).getDescription();
            return Objects.nonNull(description) ? description.equals(this.getDescription()) : false;
        }
    }
}
