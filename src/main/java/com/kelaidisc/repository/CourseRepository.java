package com.kelaidisc.repository;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;
import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long>{
    Professor assignToCourse(Professor professor);

    List<Student> enrollToCourse(List<Student> students);
}
