CREATE TABLE professor
(
    id         int          NOT NULL AUTO_INCREMENT,
    first_name varchar(40)  NOT NULL,
    last_name  varchar(40)  NOT NULL,
    email      varchar(255) NOT NULL,
    phone      varchar(20) DEFAULT NULL,
    birthday   date         NOT NULL,
    unique (email),
    unique (phone),
    PRIMARY KEY (id),
    INDEX (last_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;