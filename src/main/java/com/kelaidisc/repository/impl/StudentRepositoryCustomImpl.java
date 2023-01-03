package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Student;
import com.kelaidisc.repository.StudentRepositoryCustom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@NoArgsConstructor
public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Student> findAllByNameOrEmailOrPhoneOrBirthday(String name, String email,
                                                             String phone, LocalDate birthday) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);
    Root<Student> studentRoot = criteriaQuery.from(Student.class);

    List<Predicate> predicates = new ArrayList<>();

    if (name != null) {

      Predicate firstName = cb.like(studentRoot.get("firstName"), "%" + name + "%");
      Predicate lastName = cb.like(studentRoot.get("lastName"), "%" + name + "%");

      predicates.add(cb.or(firstName, lastName));
    }

    if (email != null) {
      predicates.add(cb.equal(studentRoot.get("email"), email));
    }

    if (phone != null) {
      predicates.add(cb.equal(studentRoot.get("phone"), phone));
    }

    if (birthday != null) {
      predicates.add(cb.equal(studentRoot.get("birthday"), birthday));
    }

    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    return entityManager
        .createQuery(criteriaQuery)
        .getResultList();
  }
}