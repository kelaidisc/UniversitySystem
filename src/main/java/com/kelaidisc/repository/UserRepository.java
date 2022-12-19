package com.kelaidisc.repository;

import com.kelaidisc.domain.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

  @NotNull List<T> findAll();

  List<T> findAllByFirstNameContainingIgnoreCase(@Param("firstName") String firstName);

  List<T> findAllByLastNameContainingIgnoreCase(@Param("lastName") String lastName);

  List<T> findAllByBirthday(@Param("birthday") LocalDate birthday);

  T findByEmail(@Param("email") String email);

  T findByPhone(@Param("phone") String phone);

  void deleteAllByIdIn(Set<Long> ids);

  boolean existsByEmailAndIdIsNot(String email, Long id);

  boolean existsByPhoneAndIdIsNot(String phone, Long id);
}
