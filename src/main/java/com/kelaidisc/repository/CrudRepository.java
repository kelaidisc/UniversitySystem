package com.kelaidisc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CrudRepository<T, ID> {
    List<T> findAll();

    List<T> findAllByFirstNameLike(String firstName);

    List<T> findAllByLastNameLike(String lastName);

    List<T> findAllByBirthday(LocalDate birthday);

    T findById(ID id);

    T findByEmail(String email);

    T findByPhone(String phone);

    T create(T object);

    T update(T object);

    void deleteByIds(Set<ID> ids);
}
