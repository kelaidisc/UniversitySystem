package com.kelaidisc.service;

import com.kelaidisc.domain.Course;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.repository.CourseRepository;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CourseService {
  private final CourseRepository courseRepository;

  public List<Course> findAll() {
    return courseRepository.findAll();
  }

  /*
    TODO ok
    1. Create a new package, call it exception
    2. Create a new class called UniversityNotFoundException and extend from RuntimeException
    3. Convert the Optional<Course> coming from the CrudRepository to Course by adding the .orElseThrow(() -> new NotFoundException());
   */
  public Course findById(@NonNull Long id) {
    return courseRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException());
  }

  public Course create(Course course) {
    return courseRepository.save(course);
  }

  public Course update(Course course) {
    return courseRepository.save(course);
  }

  public void deleteByIds(@NonNull Set<Long> ids) {
    courseRepository.deleteAllById(ids);
  }

  // TODO Create this in the CrudRepository ok
  public List<Course> findAllByNameLike(String name) {
    return courseRepository.findAllByNameLike(name);
  }

  // TODO How does this work right now? Now we have Hibernate as ORM ok
  // TODO How does this work right now? Now we have Hibernate as ORM ok

}
