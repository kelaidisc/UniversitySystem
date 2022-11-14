package com.kelaidisc.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
@SuperBuilder
@Entity(name = "professor")
public class Professor extends User {

  // TODO What does CascadeType.ALL doing here? Do you need it? So far, you just want
  //  to link a Professor with courses, but you do not want to auto create courses when creating professors
  @ToString.Exclude
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
  private Set<Course> courses;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Professor professor = (Professor) o;
    return getId() != null && Objects.equals(getId(), professor.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
