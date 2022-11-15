package com.kelaidisc.repository;

import com.kelaidisc.domain.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

  List<T> findAll();

  List<T> findAllByFirstNameLike(@Param("firstName") String firstName);

  List<T> findAllByLastNameLike(@Param("lastName") String lastName);

  List<T> findAllByBirthday(LocalDate birthday);

  T findByEmail(String email);

  T findByPhone(String phone);

  boolean existsByLastNameAndFirstName(String lastName, String firstName);
}
