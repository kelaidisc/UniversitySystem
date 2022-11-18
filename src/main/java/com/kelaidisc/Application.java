package com.kelaidisc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  // TODO Test that deletions work as expected. Delete a course, delete a student, delete a professor
  //  Course deletion should not trigger professor or student deletion
  //  Professor deletion should not trigger student or course deletion
  //  Student deletion should not trigger professor or course deletion
  //  When a course is deleted, the students-courses table should also be cleared by the corresponding rows
  //  When a student is deleted the student-courses table should also be cleared by the corresponding rows
  //  When a professor is deletes the courses he teaches should also be updated with the professorId = null

  // TODO Add Swagger API ok
  // TODO Create an ExceptionHandler
  //  https://www.baeldung.com/exception-handling-for-rest-with-spring



  // TODO For the future
  //  1. Flyway
  //  2. Spring Conversion Service + MapStruct
  //  3. Unit Tests / Integration Tests
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
