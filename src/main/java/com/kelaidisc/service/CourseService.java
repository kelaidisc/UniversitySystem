package com.kelaidisc.service;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.repository.CourseRepository;
import com.kelaidisc.repository.ProfessorRepository;
import com.kelaidisc.repository.StudentRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

  private final CourseRepository courseRepository;
  private final ProfessorRepository professorRepository;

  private final StudentRepository studentRepository;

  public List<Course> findAll() {
    return courseRepository.findAll();
  }

  // TODO Create a new method names find(Long id) that returns an Optional<Course>

  // TODO Rename this method to findOrThrow
  public Course findById(Long id) {
    return courseRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(Course.class, id));
  }

  public Course create(Course course) {

    if (courseRepository.existsByName(course.getName())) {
      // TODO The fieldName should be "name" and you should also create a new field named fieldValue and that should be course.getName
      //  so it would be smth like this throw new UniversityDuplicateResourceException(Course.class, "name", course.getName());
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

    if (courseRepository.existsByName(course.getName())) {
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
    Optional<Course> courseOptional = courseRepository.findById(
        courseId);  // TODO You should use the findById that you created that returns an object or throws an exception
    Optional<Professor> professorOptional = professorRepository.findById(
        professorId);  // TODO You should use the findById that you created that returns an object or throws an exception

    if (courseOptional.isPresent()) {
      Course course = courseOptional.get();

      if (professorOptional.isPresent()) {
        Professor professor = professorOptional.get();
        course.setProfessor(professor);
      } else {
        throw new UniversityNotFoundException(Professor.class, professorId);
      }
    } else {
      throw new UniversityNotFoundException(Course.class, courseId);
    }
  }

  // TODO You should use the findById that you created that returns an object or throws an exception
  public void removeProfessorFromCourse(Long courseId) {

    Optional<Course> courseOptional = courseRepository.findById(courseId);

    if (courseOptional.isPresent()) {
      Course course = courseOptional.get();

      course.setProfessor(null);
      courseRepository.save(course);

    } else {
      throw new UniversityNotFoundException(Course.class, courseId);
    }
  }

  public void enrollStudents(Long courseId, Set<Long> ids) {
    Optional<Course> courseOptional =
        courseRepository.findById(
            courseId); // TODO You should use the findById that you created that returns an object or throws an exception


    // TODO  Do not use Objects.requireNonNull here. Just validate in controller
    // TODO Do not use studentRepository::findById. Create a similar method that does something like findAllByIds(Set<Long> ids)
    //  Most of the times you want do not want to spam queries to the database when you can just do one query and get all the results you need
    Set<Student> students = Objects.requireNonNull(ids.stream()
            .map(studentRepository::findById)
            .findFirst().orElse(null))
        .stream().collect(Collectors.toSet());


    if (courseOptional.isPresent()) {
      Course course = courseOptional.get();
      course.setStudents(students);
    } else {
      throw new UniversityNotFoundException(Course.class, courseId);
    }
  }

  // TODO Do the same things as mentioned in the above method
  public void disEnrollStudents(Long courseId, Set<Long> ids) {

    Optional<Course> courseOptional = courseRepository.findById(courseId);

    Set<Student> students = Objects.requireNonNull(ids.stream()
            .map(studentRepository::findById)
            .findFirst().orElse(null))
        .stream().collect(Collectors.toSet());

    if (courseOptional.isPresent()) {
      Course course = courseOptional.get();
      course.getStudents().removeAll(students);
    } else {
      throw new UniversityNotFoundException(Course.class, courseId);
    }
  }
}
