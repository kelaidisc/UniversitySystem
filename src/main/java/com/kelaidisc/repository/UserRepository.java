package com.kelaidisc.repository;

import com.kelaidisc.domain.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface UserRepository<T extends User> extends BaseRepository<T>{
    List<T> findAllByFirstNameLike(@Param("firstName") String firstName);
    List<T> findAllByLastNameLike(@Param("lastName") String lastName);
    List<T> findAllByBirthday(LocalDate birthday);
    T findByEmail(String email);
    T findByPhone(String phone);
}
