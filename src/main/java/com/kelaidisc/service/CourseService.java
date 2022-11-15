package com.kelaidisc.service;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.repository.CourseRepository;
import com.kelaidisc.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;
    private final ProfessorService professorService;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    // TODO Create a new method names find(Long id) that returns an Optional<Course> ok
    public Optional<Course> find(Long id) {
        return courseRepository.findById(id);
    }

    // TODO Rename this method to findOrThrow ok
    public Course findOrThrow(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(Course.class, id));
    }

    public Course create(Course course) {

        if (courseRepository.existsByName(course.getName())) {
            // TODO The fieldName should be "name" and you should also create a new field named fieldValue and that should be course.getName
            //  so it would be smth like this throw new UniversityDuplicateResourceException(Course.class, "name", course.getName()); ok
            throw new UniversityDuplicateResourceException(Course.class, "name", course.getName());
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
            throw new UniversityDuplicateResourceException(Course.class, "name", course.getName());
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
        // TODO You should use the findById that you created that returns an object or throws an exception ok
        // TODO You should use the findById that you created that returns an object or throws an exception ok

        Course course = findOrThrow(courseId);
        Professor professor = professorService.findOrThrow(professorId);
        course.setProfessor(professor);

    }

    // TODO You should use the findById that you created that returns an object or throws an exception ok
    public void removeProfessorFromCourse(Long courseId) {

        Course course = findOrThrow(courseId);
        course.setProfessor(null);
        courseRepository.save(course);

    }

    public void enrollStudents(Long courseId, Set<Long> ids) {

        // TODO You should use the findById that you created that returns an object or throws an exception ok
        // TODO  Do not use Objects.requireNonNull here. Just validate in controller
        // TODO Do not use studentRepository::findById. Create a similar method that does something like findAllByIds(Set<Long> ids) ok
        // Most of the times you want do not want to spam queries to the database when you can just do one query and get all the results you need

        Course course = findOrThrow(courseId);
        Set<Student> students = studentRepository.findAllByIdIn(ids);
        course.setStudents(students);

    }

    // TODO Do the same things as mentioned in the above method ok
    public void disEnrollStudents(Long courseId, Set<Long> ids) {

        Course course = findOrThrow(courseId);
        Set<Student> students = studentRepository.findAllByIdIn(ids);
        course.getStudents().removeAll(students);

    }
}
