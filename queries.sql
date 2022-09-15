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