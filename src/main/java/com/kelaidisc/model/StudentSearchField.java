package com.kelaidisc.model;

public enum StudentSearchField {
    FIRST_NAME(0,"First Name"),
    LAST_NAME(1,"Last Name"),
    EMAIL(2,"Email"),
    PHONE(3,"Phone"),
    BIRTHDAY(4,"Birthday");

    private final int index;
    private final String label;

    StudentSearchField(int index, String label) {
        this.index = index;
        this.label = label;
    }

    public int getIndex() {
        return index;
    }

    public String getLabel() {
        return label;
    }

    public static StudentSearchField fromIndex(int index){
        for(StudentSearchField searchField : StudentSearchField.values()){
            if(index == searchField.getIndex()){
                return searchField;
            }
        }
        return null;
    }
}
