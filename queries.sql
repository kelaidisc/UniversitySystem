INSERT INTO university.professor
(id, first_name, last_name, email, phone, birthday)
VALUES(0, 'John', 'Giannopoulos', 'johnnyg@gmail.com', '+30 6976770000', '1984-07-08');
-----------------------------
INSERT INTO university.professor
(id, first_name, last_name, email, phone, birthday)
VALUES(1, 'Andy', 'Mazurcak', 'andym@hotmail.com', '+30 6999030405', '1983-09-10');
-----------------------------
INSERT INTO university.professor
(id, first_name, last_name, email, phone, birthday)
VALUES(2, 'Panos', 'Bazios', 'baziospan@gmail.com', '+30 6979011011', '1988-12-03');
-----------------------------
INSERT INTO university.professor
(id, first_name, last_name, email, phone, birthday)
VALUES(3, 'Aglaia', 'Gioka', 'aiglig@gmail.com', '+30 6923040404', '1978-01-01');
-----------------------------
INSERT INTO university.professor
(id, first_name, last_name, email, phone, birthday)
VALUES(4, 'Ifigeneia', 'Polyzou', 'polifi@gmail.com', '+30 6914201420', '1979-03-03');
-----------------------------
INSERT INTO university.student
(id, first_name, last_name, email, phone, birthday)
VALUES(0, 'Thomas', 'Kefalas', 'kefthom@gmail.com', '+30 6994545556', '1992-03-04');
-----------------------------
INSERT INTO university.student
(id, first_name, last_name, email, phone, birthday)
VALUES(1, 'Alexandra', 'Kelaidi', 'alexkel12@hotmail.com', '+30 6922324252', '1997-06-12');
-----------------------------
INSERT INTO university.student
(id, first_name, last_name, email, phone, birthday)
VALUES(2, 'Themis', 'Pantzouris', 'themipantz@hotmail.com', '+30 6944454554', '1999-09-04');
-----------------------------
INSERT INTO university.student
(id, first_name, last_name, email, phone, birthday)
VALUES(3, 'Ioannis', 'Ioannou', 'ioannisioan@gmail.com', '+30 6991911132', '2001-02-22');
-----------------------------
INSERT INTO university.student
(id, first_name, last_name, email, phone, birthday)
VALUES(4, 'Maria', 'Papadopoulos', 'papa05mar@gmail.com', '+30 6922340599', '2003-05-05');
-----------------------------
INSERT INTO university.course
(id, name)
VALUES(0, '');
-----------------------------
UPDATE university.professor
SET first_name='', last_name='', email='', phone='', birthday=''
WHERE id=;
-----------------------------
UPDATE university.student
SET first_name='', last_name='', email='', phone='', birthday=''
WHERE id=;
-----------------------------
UPDATE university.course
SET name=''
WHERE id=;
-----------------------------
DELETE FROM university.professor
WHERE id=;
-----------------------------
DELETE FROM university.professor
WHERE id in();
-----------------------------
DELETE FROM university.student
WHERE id=;
-----------------------------
DELETE FROM university.student
WHERE id in();
-----------------------------
DELETE FROM university.course
WHERE id=;
-----------------------------
DELETE FROM university.course
WHERE id in();
-----------------------------
SELECT *
FROM university.professor;
-----------------------------
SELECT *
FROM university.professor where id=;
-----------------------------
SELECT *
FROM university.professor where first_name like '%%';
-----------------------------
SELECT *
FROM university.professor where last_name like '%%';
-----------------------------
SELECT *
FROM university.professor where email='';
-----------------------------
SELECT *
FROM university.professor where phone='';
-----------------------------
SELECT *
FROM university.professor where birthday='';
-----------------------------
SELECT *
FROM university.student;
-----------------------------
SELECT *
FROM university.student where id=;
-----------------------------
SELECT *
FROM university.student where first_name like '%%';
-----------------------------
SELECT *
FROM university.student where last_name like '%%';
-----------------------------
SELECT *
FROM university.student where email='';
-----------------------------
SELECT *
FROM university.student where phone='';
-----------------------------
SELECT *
FROM university.student where birthday='';
-----------------------------
SELECT *
FROM university.course;
-----------------------------
SELECT *
FROM university.course where id=;
-----------------------------
SELECT *
FROM university.course where name like '%%';