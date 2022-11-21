package com.kelaidisc.repository;

import com.kelaidisc.domain.Course;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

  List<Course> findAll();

  @Query("from course where name like %:name%")
  List<Course> findAllByNameEqualsIgnoreCaseLike(@Param("name") String name);

  boolean existsByName(String name);

  boolean existsByNameAndIdIsNot(String name, Long id);

  void deleteAllByIdIn(Set<Long> ids);

}
