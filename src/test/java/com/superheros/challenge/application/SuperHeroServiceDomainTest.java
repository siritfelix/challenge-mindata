package com.superheros.challenge.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.superheros.challenge.TestUtil;
import com.superheros.challenge.infrastructure.db.repository.SuperHeroRepository;
import com.superheros.challenge.infrastructure.rest.converter.SuperHeroConverter;
import com.superheros.challenge.shared.config.MenssageResponse;
import com.superheros.challenge.shared.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
public class SuperHeroServiceDomainTest {

    @Mock
    private SuperHeroRepository superHeroRepository;
    @Mock
    private MenssageResponse menssageResponse;

    @InjectMocks
    private SuperHeroServiceDomainImpl superHeroServiceDomainImpl;

    @Test
    public void findAllOk() {
        Integer size = 2;
        when(superHeroRepository.findAll()).thenReturn(TestUtil.buildSuperHeroEntityList(2));
        assertEquals(size, superHeroServiceDomainImpl.findAll().size());
    }

    @Test
    public void findByIdOk() {
        Integer id = TestUtil.ID_1;
        when(superHeroRepository.findById(TestUtil.ID_1)).thenReturn(TestUtil.buildSuperHeroEntityOptional(id, id));
        assertEquals(id, superHeroServiceDomainImpl.findById(id).getId());
    }

    @Test
    public void findByIdNotFoundException() {
        when(menssageResponse.getMessages()).thenReturn(TestUtil.buidlMenssageResponse());
        Integer id = TestUtil.ID_1;
        when(superHeroRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            superHeroServiceDomainImpl.findById(id);
        });
    }

    @Test
    public void createOk() {
        Integer id = TestUtil.ID_1;
        when(superHeroRepository.save(any()))
                .thenReturn(TestUtil.buildSuperHeroEntityOptional(id, id).get());
        assertEquals(id, superHeroServiceDomainImpl
                .create(SuperHeroConverter.toSuperHero(TestUtil.buildSuperHeroEntityOptional(id, id).get())).getId());
    }

    @Test
    public void upDateOk() {
        Integer id = TestUtil.ID_1;
        when(superHeroRepository.findById(TestUtil.ID_1)).thenReturn(TestUtil.buildSuperHeroEntityOptional(id, id));
        when(superHeroRepository.save(any()))
                .thenReturn(TestUtil.buildSuperHeroEntityOptional(id, id).get());
        assertEquals(id, superHeroServiceDomainImpl
                .upDate(SuperHeroConverter.toSuperHero(TestUtil.buildSuperHeroEntityOptional(id, id).get())).getId());
    }

    @Test
    public void upDateNotFoundException() {
        when(menssageResponse.getMessages()).thenReturn(TestUtil.buidlMenssageResponse());
        Integer id = TestUtil.ID_1;

        assertThrows(NotFoundException.class, () -> {
            superHeroServiceDomainImpl
                    .upDate(SuperHeroConverter.toSuperHero(TestUtil.buildSuperHeroEntityOptional(id, id).get()));
        });
    }

    @Test
    public void removeByIdOk() {
        Integer id = TestUtil.ID_1;
        when(superHeroRepository.findById(TestUtil.ID_1)).thenReturn(TestUtil.buildSuperHeroEntityOptional(id, id));
        superHeroServiceDomainImpl.removeById(id);
        verify(superHeroRepository).delete(any());
    }

    @Test
    public void removeByIdNotFoundException() {
        when(menssageResponse.getMessages()).thenReturn(TestUtil.buidlMenssageResponse());
        Integer id = TestUtil.ID_1;
        assertThrows(NotFoundException.class, () -> {
            superHeroServiceDomainImpl.removeById(id);
        });
    }

    @Test
    public void findByAllByFilterOk() {
        Integer size = 2;
        when(superHeroRepository.findByNameContaining(any())).thenReturn(TestUtil.buildSuperHeroEntityList(2));
        assertEquals(size, superHeroServiceDomainImpl.findByAllByFilter(any()).size());
    }
}
