package com.kelaidisc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  /*
    ToDo
      1. Checkstyle
        a. Read here https://www.baeldung.com/checkstyle-java (or anything else you may find on your own)
        b. Add checkstyle plugin in IntelliJ and configure it to use the custom-google-checkstyle.xml that I have included under resources/config/
        c. Add checkstyle maven plugin and configure your project to fail if the rules
      2. Flyway
        a. Read here https://www.baeldung.com/database-migrations-with-flyway (or anything else you may find on your own)
        b. Add Flyway to your project and convert schema.sql and data.sql scripts Flyway migration scripts
      3. MapStruct - Spring Conversion Service
        a. Read here https://www.baeldung.com/mapstruct (or anything else you may find on your own)
        b. Add MapStruct to your project as a dependency
        c. Change Converters to use MapStruct internally
        d. Read here https://www.baeldung.com/spring-type-conversions (or anything else you may find on your own)
        e. Create the appropriate configuration to your project for the Conversion Service
        f. Register all the Converters to the Conversion Service of your project
        g. Inject and use your Conversion Service in all the places where you convert something
      4. Exceptions
        a. Create a parent Exception that extends from RuntimeException (name it UniversityException or smth)
        b. Change all your different exceptions (BadRequest, Duplicate, NotFound) to extend UniversityException
        c. Rename ApiExceptionHandler to UniversityExceptionHandler
        d. Change the implementation of UniversityExceptionHandler in a way that will handle all kind UniversityExceptions the same way
          d.1. In order to do these you may need to add an extra (constant) field in the UniversityException classes
      5. Unit Tests (pending)
      6. Integration Tests (pending)
   */

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
