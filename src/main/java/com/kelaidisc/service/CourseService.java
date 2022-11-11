package com.kelaidisc.service;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.repository.CourseRepository;
import com.kelaidisc.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

  // TODO Service Validations - Try to reuse the validations for the different methods ok i guess
  private final CourseRepository courseRepository;
  private final ProfessorRepository professorRepository;

  public List<Course> findAll() {
    return courseRepository.findAll();
  }

  // TODO Add two arguments in the UniversityNotFoundException Class & ID
  //  and pass in the Course.class & id that was used and not found ok
  public Course findById(Long id) {
    return courseRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(Course.class, id));
  }

  /*
  // TODO Check if there is another Course with the same name.
       If yes throw a UniversityDuplicateResourceException with the following arguments (Class, fieldName)
        e.g. (Course, name) ok
   */
  public Course create(Course course) {

    if(courseRepository.existsByName(course.getName())){
      throw new UniversityDuplicateResourceException(Course.class, course.getName());
    }
    return courseRepository.save(course);

  }

  /*
  // TODO Check if there is "another" Course with the same name.
       If yes throw a UniversityDuplicateResourceException with the following arguments (Class, fieldName)
        e.g. (Course, name) ok
  */
  public Course update(Course course) {

    if(courseRepository.existsByName(course.getName())){
      throw new UniversityDuplicateResourceException(Course.class, course.getName());
    }
    return courseRepository.save(course);

  }

  public void deleteByIds(Set<Long> ids) {
    courseRepository.deleteAllById(ids);
  }

  public List<Course> findAllByNameLike(String name) {
    return courseRepository.findAllByNameLike(name);
  }

  public void assignProfessorToCourse(Long courseId, Long professorId) {
    // TODO Add the implementation ok
    Optional<Course> courseOptional = courseRepository.findById(courseId);
    Optional<Professor> professorOptional = professorRepository.findById(professorId);

    if(courseOptional.isPresent()) {
      Course course = courseOptional.get();

      if(professorOptional.isPresent()){
        Professor professor = professorOptional.get();
        course.setProfessor(professor);
      } else {
        throw new UniversityNotFoundException(); //can't use generics to Class<Gen extends BaseEntity> in my custom exception
      }
    } else {
      throw new UniversityNotFoundException(Course.class, courseId);
    }
  }

  public void removeProfessorFromCourse(Long courseId) {
    // TODO Add the implementation ok

    Optional<Course> courseOptional = courseRepository.findById(courseId);

    if(courseOptional.isPresent()){
      Course course = courseOptional.get();

      course.setProfessor(null);
      courseRepository.save(course);

    } else {
      throw new UniversityNotFoundException(Course.class, courseId);
    }
  }
}
