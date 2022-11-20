package com.kelaidisc.service;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.repository.CourseRepository;
import com.kelaidisc.repository.StudentRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

  private final CourseRepository courseRepository;

  private final StudentRepository studentRepository;
  private final ProfessorService professorService;

  public List<Course> findAll() {
    return courseRepository.findAll();
  }

  public Optional<Course> find(Long id) {
    return courseRepository.findById(id);
  }

  @Transactional
  public Course findOrThrow(Long id) {
    return courseRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(Course.class, id));
  }

  public Course create(Course course) {
    validateNameEligibility(course);
    return courseRepository.save(course);

  }

  public Course update(Course course) {
    validateNameEligibility(course);
    return courseRepository.save(course);
  }

  private void validateNameEligibility(Course course) {
    // TODO
    //  All these conditional branches can be checked probably in a single query (or two for create and update)
    //  You need to change your condition/query in the if statement to make sure that:
    //  If a course is created with the name X:
    //    If there is not other course named X, we will allow it
    //    If there is another course named X, we will not allow it
    //  If a course is updated with the name X:
    //    If there is not another course named X, we will allow it
    //    If there is another course named X:
    //      If it is the same as the one that is updated, we will allow it
    //      If it is not the same as the one that is updated, we will not allow it
    if (courseRepository.existsByName(course.getName())) {
      throw new UniversityDuplicateResourceException(Course.class, "name", course.getName());
    }
  }

  public void deleteByIds(Set<Long> ids) {
    courseRepository.deleteAllByIdIn(ids);
  }

  public List<Course> findAllByNameLike(String name) {
    return courseRepository.findAllByNameEqualsIgnoreCaseLike(name);
  }


  public void assignProfessorToCourse(Long courseId, Long professorId) {

    Course course = findOrThrow(courseId);
    Professor professor = professorService.findOrThrow(professorId);
    course.setProfessor(professor);
    courseRepository.save(course);
  }

  public void removeProfessorFromCourse(Long courseId) {

    Course course = findOrThrow(courseId);
    course.setProfessor(null);
    courseRepository.save(course);

  }

  public void enrollStudents(Long courseId, Set<Long> ids) {

    Course course = findOrThrow(courseId);
    Set<Student> studentsForEnroll = studentRepository.findAllByIdIn(ids);
    Set<Student> studentsAlreadyEnrolled = course.getStudents();
    Set<Student> mergedSet = new HashSet<>();
    Stream.of(studentsForEnroll, studentsAlreadyEnrolled)
        .forEach(mergedSet::addAll);
    course.setStudents(mergedSet);
    courseRepository.save(course);
  }

  public void disEnrollStudents(Long courseId, Set<Long> ids) {

    Course course = findOrThrow(courseId);
    Set<Student> students = studentRepository.findAllByIdIn(ids);
    course.getStudents().removeAll(students);
    courseRepository.save(course);
  }
}
