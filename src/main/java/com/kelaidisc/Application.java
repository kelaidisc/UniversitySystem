package com.kelaidisc;

import com.kelaidisc.model.ProfessorSearchField;
import java.util.Arrays;
import java.util.Scanner;

public class Application {

  public static void main(String[] args) {

  }

  private static void printProfSearchFields() {
    System.out.println("Choose search field");
    Arrays.stream(ProfessorSearchField.values())
        .sorted()
        .forEach(e -> System.out.println(e.getIndex() + e.getLabel()));

    var input = Integer.valueOf(new Scanner(System.in).nextLine().trim());
    var searchField = ProfessorSearchField.fromIndex(input);

    var inputSearchTerm = new Scanner(System.in).nextLine();
    System.out.println("Psaxnw " + searchField + " " + inputSearchTerm);
  }
}
