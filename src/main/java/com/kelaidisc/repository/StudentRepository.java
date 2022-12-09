package com.kelaidisc.repository;

import com.kelaidisc.domain.Student;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends UserRepository<Student> {
  Set<Student> findAllByIdIn(Set<Long> ids);
}
