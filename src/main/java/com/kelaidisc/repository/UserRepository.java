package com.kelaidisc.repository;

import com.kelaidisc.domain.User;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

  void deleteAllByIdIn(Set<Long> ids);

  boolean existsByEmailAndIdIsNot(String email, Long id);

  boolean existsByPhoneAndIdIsNot(String phone, Long id);
}