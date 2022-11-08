package com.kelaidisc.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
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

  // TODO Why is this transient?
  @Transient
  private String description;

  // TODO What does CascadeType.ALL means? Do you actually want this?
  @JoinColumn(name = "professor_id")
  @ManyToOne(cascade = CascadeType.ALL)
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
