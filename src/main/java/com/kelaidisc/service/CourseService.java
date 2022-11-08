package com.kelaidisc.service;

import com.kelaidisc.domain.Course;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.repository.CourseRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CourseService {

  // TODO Service Validations - Try to reuse the validations for the different methods
  private final CourseRepository courseRepository;

  public List<Course> findAll() {
    return courseRepository.findAll();
  }

  // TODO Add two arguments in the UniversityNotFoundException Class & ID and pass in the Course.class & id that was used and not found
  public Course findById(Long id) {
    return courseRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException());
  }

  /*
  // TODO Check if there is another Course with the same name. If yes throw a UniversityDuplicateResourceException with the following arguments (Class, fieldName) e.g. (Course, name)
   */
  public Course create(Course course) {
    return courseRepository.save(course);
  }

  /*
  // TODO Check if there is "another" Course with the same name. If yes throw a UniversityDuplicateResourceException with the following arguments (Class, fieldName) e.g. (Course, name)
  */
  public Course update(Course course) {
    return courseRepository.save(course);
  }

  public void deleteByIds(Set<Long> ids) {
    courseRepository.deleteAllById(ids);
  }

  public List<Course> findAllByNameLike(String name) {
    return courseRepository.findAllByNameLike(name);
  }

  public void assignProfessorToCourse(Long courseId, Long professorId) {
    // TODO Add the implementation
  }

  public void removeProfessorFromCourse(Long courseId) {
    // TODO Add the implementation
  }
}
