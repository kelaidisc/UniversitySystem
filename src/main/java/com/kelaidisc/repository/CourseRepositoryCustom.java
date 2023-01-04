package com.kelaidisc.repository;

import com.kelaidisc.domain.Course;
import java.util.List;

public interface CourseRepositoryCustom {

  List<Course> findAllByName(String name);
}