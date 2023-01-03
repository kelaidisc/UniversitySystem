package com.kelaidisc.repository;

import com.kelaidisc.domain.Professor;
import java.time.LocalDate;
import java.util.List;

public interface ProfessorRepositoryCustom {

  List<Professor> findAllByNameOrEmailOrPhoneOrBirthday(String name, String email, String phone,
                                                        LocalDate birthday);
}