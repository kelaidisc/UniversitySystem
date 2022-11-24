CREATE TABLE IF NOT EXISTS course_students
(
    course_id  int not null,
    student_id int not null,
    unique (course_id, student_id),
    foreign key (course_id) references course (id),
    foreign key (student_id) references student (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;