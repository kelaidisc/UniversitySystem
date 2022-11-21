package com.kelaidisc.repository;

import com.kelaidisc.domain.Professor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends UserRepository<Professor> {

  @Query("from professor where firstName like %:firstName%")
  List<Professor> findAllByFirstNameEqualsIgnoreCaseLike(@Param("firstName") String firstName);

  @Query("from professor where lastName like %:lastName%")
  List<Professor> findAllByLastNameEqualsIgnoreCaseLike(@Param("lastName") String lastName);
}
