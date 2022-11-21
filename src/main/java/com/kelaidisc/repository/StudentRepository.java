package com.kelaidisc.repository;

import com.kelaidisc.domain.Student;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends UserRepository<Student> {
  Set<Student> findAllByIdIn(Set<Long> ids);

  @Query("from student where firstName like %:firstName%")
  List<Student> findAllByFirstNameEqualsIgnoreCaseLike(@Param("firstName") String firstName);

  @Query("from student where lastName like %:lastName%")
  List<Student> findAllByLastNameEqualsIgnoreCaseLike(@Param("lastName") String lastName);
}
