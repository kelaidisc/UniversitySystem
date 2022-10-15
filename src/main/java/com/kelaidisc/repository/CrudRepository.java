package com.kelaidisc.repository;

import java.util.List;
import java.util.Set;

public interface CrudRepository<T, ID> {
    List<T> findAll();

    T findById(ID id);

    T create(T object);

    T update(T object);

    void deleteByIds(Set<ID> ids);
}
