CREATE TABLE course
(
    id            int         NOT NULL AUTO_INCREMENT,
    `name`        varchar(60) NOT NULL,
    `description` text        NOT NULL,
    professor_id  int,
    foreign key (professor_id) references professor (id),
    unique (`name`),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;