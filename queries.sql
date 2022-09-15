INSERT INTO university.professor
(id, first_name, last_name, email, phone, birthday)
VALUES(0, '', '', '', '', '');
-----------------------------
INSERT INTO university.student
(id, first_name, last_name, email, phone, birthday)
VALUES(0, '', '', '', '', '');
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