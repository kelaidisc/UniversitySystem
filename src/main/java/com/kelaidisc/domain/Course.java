package com.kelaidisc.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "course")
public class Course extends BaseEntity {

  @Column(name = "name",nullable = false, unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @JoinColumn(name = "professor_id")
  @ManyToOne(cascade = CascadeType.ALL)
  private Professor professor;

  @ToString.Exclude
  @ManyToMany(mappedBy = "courses")
  private Set<Student> students;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Course course = (Course) o;
    return getId() != null && Objects.equals(getId(), course.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
