package com.kelaidisc.repository;

import com.kelaidisc.domain.Student;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentRepository extends UserRepository<Student> {
    Set<Student> findAllByIdIn(Set<Long> ids);
}
