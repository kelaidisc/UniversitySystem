package com.kelaidisc.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "course")
public class Course extends BaseEntity {

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  // TODO Why is this transient? ok
  @Column(name = "description", nullable = false)
  private String description;

  // TODO What does CascadeType.ALL means? Do you actually want this? ok
  // it means PERSIST, REMOVE, REFRESH, MERGE, DETACH are being done also to the related entities = my bad
  @JoinColumn(name = "professor_id")
  @ManyToOne
  private Professor professor;

  @ToString.Exclude
  @ManyToMany(mappedBy = "courses")
  private Set<Student> students;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Course course = (Course) o;
    return getId() != null && Objects.equals(getId(), course.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
