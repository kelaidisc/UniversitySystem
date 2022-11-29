# --drop table
# CREATE TABLE professor
# (
#     id         int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     first_name varchar(40)  NOT NULL,
#     last_name  varchar(40)  NOT NULL,
#     email      varchar(255) NOT NULL,
#     phone      varchar(20) DEFAULT NULL,
#     birthday   date         NOT NULL,
#     unique (email),
#     unique (phone)
# );
#
# --drop table
# CREATE TABLE student
# (
#     id                int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     first_name        varchar(40)  NOT NULL,
#     last_name         varchar(40)  NOT NULL,
#     email             varchar(255) NOT NULL,
#     phone             varchar(20) DEFAULT NULL,
#     birthday          date         NOT NULL,
#     registration_date date         NOT NULL,
#     unique (email),
#     unique (phone)
# );
#
# --drop table
# CREATE TABLE course
# (
#     id           int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     `name`         varchar(60) NOT NULL,
#     `description`  text        NOT NULL,
#     professor_id int,
#     foreign key (professor_id) references professor (id),
#     unique (`name`)
# );
#
# --drop table
# CREATE TABLE course_students
# (
#     course_id  int not null,
#     student_id int not null,
#     unique (course_id, student_id),
#     foreign key (course_id) references course (id),
#     foreign key (student_id) references student (id)
# );
