package com.kelaidisc.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity(name = "professor")
public class Professor extends User {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Professor professor = (Professor) o;
        return getId() != null && Objects.equals(getId(), professor.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
