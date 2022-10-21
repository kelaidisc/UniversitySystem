package com.kelaidisc.service;

import com.kelaidisc.domain.Course;
import com.kelaidisc.repository.CrudRepository;
import com.kelaidisc.repository.impl.CourseRepository;
import lombok.NonNull;

import java.util.List;
import java.util.Set;


public class CourseService {
    private final CrudRepository<Course> courseRepository = new CourseRepository();

    public List<Course> findAll(){return courseRepository.findAll();}

    public Course findById(@NonNull Long id){return courseRepository.findById(id);}

    public Course create(Course course){return courseRepository.create(course);}

    public Course update(Course course){return courseRepository.update(course);}

    public void deleteByIds(@NonNull Set<Long> ids){courseRepository.deleteByIds(ids);}

    public List<Course> findAllByNameLike(String name)
    {return ((CourseRepository) courseRepository).findAllByNameLike(name);}

    //assign professor to course
    //enroll student to course

}
