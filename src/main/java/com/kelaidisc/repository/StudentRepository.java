package com.kelaidisc.repository;

import com.kelaidisc.domain.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends UserRepository<Student> {
}
