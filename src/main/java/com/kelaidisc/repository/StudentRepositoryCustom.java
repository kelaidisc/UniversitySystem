package com.kelaidisc.repository;

import com.kelaidisc.domain.Student;
import java.time.LocalDate;
import java.util.List;

public interface StudentRepositoryCustom {

  List<Student> findAllByNameOrEmailOrPhoneOrBirthday(String name, String email, String phone,
                                                      LocalDate birthday);
}