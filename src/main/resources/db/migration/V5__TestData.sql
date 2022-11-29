INSERT IGNORE INTO professor
VALUES(1, 'John', 'Giannopoulos', 'johnnyg@gmail.com', '+30 6976770000', '1984-07-08');
INSERT IGNORE INTO professor
VALUES(2,'Andy', 'Mazurcak', 'andym@hotmail.com', '+30 6999030405', '1983-09-10');
INSERT IGNORE INTO professor
VALUES(3, 'Panos', 'Bazios', 'baziospan@gmail.com', '+30 6979011011', '1988-12-03');
INSERT IGNORE INTO professor
VALUES(4, 'Aglaia', 'Gioka', 'aiglig@gmail.com', '+30 6923040404', '1978-01-01');
INSERT IGNORE INTO professor
VALUES(5, 'Ifigeneia', 'Polyzou', 'polifi@gmail.com', '+30 6914201420', '1979-03-03');

INSERT IGNORE INTO student
VALUES(1, 'Thomas', 'Kefalas', 'kefthom@gmail.com', '+30 6994545556', '1992-03-04','2021-03-04');
INSERT IGNORE INTO student
VALUES(2, 'Alexandra', 'Kelaidi', 'alexkel12@hotmail.com', '+30 6922324252', '1997-06-12','2022-03-04');
INSERT IGNORE INTO student
VALUES(3, 'Themis', 'Pantzouris', 'themipantz@hotmail.com', '+30 6944454554', '1999-09-04','2020-01-01');
INSERT IGNORE INTO student
VALUES(4, 'Ioannis', 'Ioannou', 'ioannisioan@gmail.com', '+30 6991911132', '2001-02-22','2019-05-05');
INSERT IGNORE INTO student
VALUES(5, 'Maria', 'Papadopoulos', 'papa05mar@gmail.com', '+30 6922340599', '2003-05-05','2022-09-09');

INSERT IGNORE INTO course
VALUES(1, 'Logistics', 'Description text1', 5);
INSERT IGNORE INTO course
VALUES(2, 'Microeconomics', 'Description text2', 4);
INSERT IGNORE INTO course
VALUES(3, 'Macroeconomics', 'Description text3', 2);
INSERT IGNORE INTO course
VALUES(4, 'Sociology', 'Description text4', 1);
INSERT IGNORE INTO course
VALUES(5, 'Agricultural Economics', 'Description text5', 2);

INSERT IGNORE INTO course_students
VALUES(1, 1);
INSERT IGNORE INTO course_students
VALUES(1, 2);
INSERT IGNORE INTO course_students
VALUES(1, 3);
INSERT IGNORE INTO course_students
VALUES(2, 1);
INSERT IGNORE INTO course_students
VALUES(2, 2);
INSERT IGNORE INTO course_students
VALUES(2, 3);
INSERT IGNORE INTO course_students
VALUES(2, 4);
INSERT IGNORE INTO course_students
VALUES(3, 1);
INSERT IGNORE INTO course_students
VALUES(3, 2);
INSERT IGNORE INTO course_students
VALUES(3, 5);
INSERT IGNORE INTO course_students
VALUES(4, 1);
INSERT IGNORE INTO course_students
VALUES(4, 4);
INSERT IGNORE INTO course_students
VALUES(5, 5);
