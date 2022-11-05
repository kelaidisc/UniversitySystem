package com.kelaidisc.repository;

import com.kelaidisc.domain.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;


@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends CrudRepository<T, Long> {
    <S extends T> S save(S entity);

    Optional<T> findById(Long id);

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<Long> ids);

    void deleteAllById(Iterable<? extends Long> ids);
}
