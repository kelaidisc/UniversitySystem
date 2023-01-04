package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Course;
import com.kelaidisc.repository.CourseRepositoryCustom;
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
public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Course> findAllByName(String name) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Course> criteriaQuery = cb.createQuery(Course.class);
    Root<Course> courseRoot = criteriaQuery.from(Course.class);

    List<Predicate> predicates = new ArrayList<>();

    if (name != null) {
      predicates.add(cb.like(courseRoot.get("name"), "%" + name + "%"));
    }

    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    return entityManager
        .createQuery(criteriaQuery)
        .getResultList();
  }
}