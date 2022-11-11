package com.kelaidisc.repository;

import com.kelaidisc.domain.Course;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

  List<Course> findAll();

  List<Course> findAllByNameLike(@Param("name") String name);

  boolean existsByName(String name);

}
