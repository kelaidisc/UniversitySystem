CREATE TABLE IF NOT EXISTS `professor`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `first_name` varchar(40)  NOT NULL,
    `last_name`  varchar(40)  NOT NULL,
    `email`      varchar(255) NOT NULL,
    `phone`      varchar(20) DEFAULT NULL,
    `birthday`   date         NOT NULL,
    unique (email, phone),
    PRIMARY KEY (`id`),
    INDEX(last_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `student`
(
    `id`                int          NOT NULL AUTO_INCREMENT,
    `first_name`        varchar(40)  NOT NULL,
    `last_name`         varchar(40)  NOT NULL,
    `email`             varchar(255) NOT NULL,
    `phone`             varchar(20) DEFAULT NULL,
    `birthday`          date         NOT NULL,
    `registration_date` date         NOT NULL,
    unique (email, phone),
    PRIMARY KEY (`id`),
    INDEX(last_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `course`
(
    `id`           int         NOT NULL AUTO_INCREMENT,
    `name`         varchar(60) NOT NULL,
    'description'  text NOT NULL,
    `professor_id` int,
    foreign key (professor_id) references professor (id),
    unique (name),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `course_students`
(
    `course_id`  int not null,
    `student_id` int not null,
    foreign key (course_id) references course (id),
    foreign key (student_id) references student (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;