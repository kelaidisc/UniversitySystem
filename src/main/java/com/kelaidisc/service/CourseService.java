package com.kelaidisc.service;

import com.kelaidisc.domain.Course;
import com.kelaidisc.repository.CourseRepository;
import com.kelaidisc.repository.impl.MySqlCourseRepositoryImpl;
import lombok.NonNull;

import java.util.List;
import java.util.Set;


public class CourseService {
    private final CourseRepository courseRepository = new MySqlCourseRepositoryImpl();

    public List<Course> findAll(){return courseRepository.findAll();}

    public List<Course> findAllByNameLike(String name){return courseRepository.findAllByNameLike(name);}

    public Course findById(@NonNull Long id){return courseRepository.findById(id);}

    public Course create(Course course){return courseRepository.create(course);}

    public Course update(Course course){return courseRepository.update(course);}

    public void deleteByIds(@NonNull Set<Long> ids){courseRepository.deleteByIds(ids);}

    //assign professor to course
    //enroll student to course



}
