package com.kelaidisc.repository;

import com.kelaidisc.domain.Course;
import java.util.List;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>, CourseRepositoryCustom {

  List<Course> findAll();

  boolean existsByNameAndIdIsNot(String name, Long id);

  void deleteAllByIdIn(Set<Long> ids);
}