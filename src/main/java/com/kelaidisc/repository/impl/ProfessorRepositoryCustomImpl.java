package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.repository.ProfessorRepositoryCustom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfessorRepositoryCustomImpl implements ProfessorRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Professor> findAllByNameOrEmailOrPhoneOrBirthday(String name, String email, String phone,
                                                               LocalDate birthday) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Professor> criteriaQuery = cb.createQuery(Professor.class);
    Root<Professor> professorRoot = criteriaQuery.from(Professor.class);

    List<Predicate> predicates = new ArrayList<>();

    if (name != null) {

      Predicate firstName = cb.like(professorRoot.get("firstName"), "%" + name + "%");
      Predicate lastName = cb.like(professorRoot.get("lastName"), "%" + name + "%");

      predicates.add(cb.or(firstName, lastName));
    }

    if (email != null) {
      predicates.add(cb.equal(professorRoot.get("email"), email));
    }

    if (phone != null) {
      predicates.add(cb.equal(professorRoot.get("phone"), phone));
    }

    if (birthday != null) {
      predicates.add(cb.equal(professorRoot.get("birthday"), birthday));
    }

    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    return entityManager
        .createQuery(criteriaQuery)
        .getResultList();
  }
}
