INSERT INTO university.professor
(id, first_name, last_name, email, phone, birthday)
VALUES(0, 'Bob', 'Strong', 'strongb@gmail.com', '+30 6977545454', '1994-08-13');
-----------------------------
INSERT INTO university.student
(id, first_name, last_name, email, phone, birthday)
VALUES(0, 'John', 'Giannopoulos', 'johnnyg@gmail.com', '+30 6976777777', '1988-03-11');
-----------------------------
INSERT INTO university.course
(id, name)
VALUES(0, 'Logistics');
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