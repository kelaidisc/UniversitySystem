package com.kelaidisc.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "student")
public class Student extends User {
  @Column(name = "registration_date", nullable = false)
  private LocalDate registrationDate;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "course_students", joinColumns = @JoinColumn(name = "student_id"),
          inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<Course> courses;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Student student = (Student) o;
    return getId() != null && Objects.equals(getId(), student.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
