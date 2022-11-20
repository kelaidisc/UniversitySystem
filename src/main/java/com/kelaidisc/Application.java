package com.kelaidisc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  // TODO Test that deletions work as expected. Delete a course, delete a student, delete a professor
  //  Course deletion should not trigger professor or student deletion ok
  //  Professor deletion should not trigger student or course deletion ok
  //  Student deletion should not trigger professor or course deletion ok
  //  When a course is deleted, the students-courses table should also be cleared by the corresponding rows ok
  //  When a student is deleted the student-courses table should also be cleared by the corresponding rows ok
  //  When a professor is deletes the courses he teaches should also be updated with the professorId = null ok

  // TODO Add Swagger API ok
  // TODO Create an ExceptionHandler ok
  //  https://www.baeldung.com/exception-handling-for-rest-with-spring ok



  // TODO For the future
  //  1. Flyway
  //  2. Spring Conversion Service + MapStruct
  //  3. Unit Tests / Integration Tests
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
