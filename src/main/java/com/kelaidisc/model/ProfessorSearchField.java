package com.kelaidisc.model;

public enum ProfessorSearchField {
  FIRST_NAME("First Name"),
  LAST_NAME("Last Name"),
  EMAIL("Email"),
  PHONE("Phone"),
  BIRTHDAY("Birthday(yyyy/mm/dd)");

  private final String label;

  ProfessorSearchField(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
