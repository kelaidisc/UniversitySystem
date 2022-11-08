INSERT INTO university_spring.professor
VALUES(1, '1984-07-08', 'johnnyg@gmail.com', 'John', 'Giannopoulos', '+30 6976770000'),
      (2, '1983-09-10', 'andym@hotmail.com','Andy', 'Mazurcak', '+30 6999030405'),
      (3, '1988-12-03', 'baziospan@gmail.com', 'Panos', 'Bazios', '+30 6979011011'),
      (4, '1978-01-01',  'aiglig@gmail.com', 'Aglaia', 'Gioka', '+30 6923040404'),
      (5, '1979-03-03', 'polifi@gmail.com', 'Ifigeneia', 'Polyzou', '+30 6914201420');

INSERT INTO university_spring.student
VALUES(1, '1992-03-04', 'kefthom@gmail.com', 'Thomas', 'Kefalas', '+30 6994545556','2021-03-04'),
      (2, '1997-06-12', 'alexkel12@hotmail.com', 'Alexandra', 'Kelaidi', '+30 6922324252','2022-03-04'),
      (3, '1999-09-04', 'themipantz@hotmail.com', 'Themis', 'Pantzouris', '+30 6944454554','2020-01-01'),
      (4, '2001-02-22', 'ioannisioan@gmail.com', 'Ioannis', 'Ioannou', '+30 6991911132','2019-05-05'),
      (5, '2003-05-05', 'papa05mar@gmail.com', 'Maria', 'Papadopoulos', '+30 6922340599','2022-09-09');

INSERT INTO university_spring.course
VALUES(1, 'Logistics', null),
      (2, 'Microeconomics', null),
      (3, 'Macroeconomics', null),
      (4, 'Sociology', 1),
      (5, 'Agricultural Economics', 2);

INSERT INTO university_spring.course_students
VALUES(1, 1),
      (1, 2),
      (1, 3),
      (2, 1),
      (2, 2),
      (2, 3),
      (2, 4),
      (3, 1),
      (3, 2),
      (3, 5),
      (4, 1),
      (4, 4),
      (5, 5);