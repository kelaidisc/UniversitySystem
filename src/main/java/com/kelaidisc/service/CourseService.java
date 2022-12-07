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
import org.jetbrains.annotations.NotNull;
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

  private void validateNameEligibility(@NotNull Course course) {
    if (course.getId() == null && (courseRepository.existsByNameAndIdIsNot(course.getName(), course.getId()))) {
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
