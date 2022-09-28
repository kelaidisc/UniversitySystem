package com.kelaidisc.repository;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;

import java.util.List;
import java.util.Set;

public interface CourseRepository {

    List<Course> findAll();

    List<Course> findAllByNameLike(String name); //despite being unique field,there might be chained Courses like Maths I, Maths II

    Course findById(Long id);

    Course create(Course course);

    Course update(Course course);

    void deleteByIds(Set<Long> ids);

    Professor assignToCourse(Professor professor);

    List<Student> enrollToCourse(List<Student> students);
}
