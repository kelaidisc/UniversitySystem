package com.kelaidisc.repository;

import com.kelaidisc.domain.User;
import java.time.LocalDate;
import java.util.List;

public interface UserRepository<T extends User> extends CrudRepository<T, Long>{
    List<T> findAllByFirstNameLike(String firstName);

    List<T> findAllByLastNameLike(String lastName);

    List<T> findAllByBirthday(LocalDate birthday);
    T findByEmail(String email);

    T findByPhone(String phone);
}
