package com.kelaidisc.model;

public enum ProfessorSearchField {
  FIRST_NAME(0, "First Name"),
  LAST_NAME(1, "Last Name"),
  EMAIL(2, "Email"),
  PHONE(3, "Phone"),
  BIRTHDAY(4, "Birthday(yyyy/mm/dd)");

  private final int index;
  private final String label;

  ProfessorSearchField(int index, String label) {
    this.index = index;
    this.label = label;
  }

  public int getIndex() {
    return index;
  }

  public String getLabel() {
    return label;
  }

  public static ProfessorSearchField fromIndex(int someIndex) {
    for (ProfessorSearchField searchField : ProfessorSearchField.values()) {
      if (someIndex == searchField.getIndex()) {
        return searchField;
      }
    }

    return null;
  }
}
