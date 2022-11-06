package com.kelaidisc.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@MappedSuperclass
public class User extends BaseEntity {

  @Column(name = "first_name", nullable = false)
  protected String firstName;

  @Column(name = "last_name", nullable = false)
  protected String lastName;

  @Column(name = "email", nullable = false, unique = true)
  protected String email;

  @Column(name = "phone", unique = true)
  protected String phone;

  @Column(name = "birthday", nullable = false)
  protected LocalDate birthday;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    User user = (User) o;
    return getId() != null && Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
