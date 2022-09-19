package com.kelaidisc.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class ProfessorManagement {

    LinkedList<Professor> list;

    public ProfessorManagement() {

        list = new LinkedList<>();
    }

    //add professor

    public void add(Professor professor){

        if(!find(professor.getId())){
            list.add(professor);
        } else{
            System.out.println("Professor already exists in the list");
        }
    }

    //find professor(if exists)

    public boolean find(int id){

        for(Professor prof : list){
            if(prof.getId() == id){
                return true;
            }
        }
        return false;
    }

    //find professor(return Professor)

    public Professor findProfessor(int id){

        for(Professor prof : list){
            if(prof.getId() == id){
                return prof;
            }
        }
        return null;
    }

    //delete professor

    public void delete(int id) {

        Professor profDeleted = null;
        for (Professor prof : list) {
            if (prof.getId() == id) {
                profDeleted = prof;
            }
        }
        if(profDeleted == null){
            System.out.println("Invalid id");
        } else{
            list.remove(profDeleted);
            System.out.println("Successfully removed from list");
        }
    }

    // update professor

    public void update(int id, Scanner scanner){ //sthn main o scanner?

        if(find(id)){
            Professor professor = findProfessor(id);

            System.out.println("What is the professor's Id?");
            professor.setId(scanner.nextInt());
            scanner.nextLine();

            System.out.println("What is the professor's First Name?");
            professor.setFirstName(scanner.nextLine());

            System.out.println("What is the professor's Last Name?");
            professor.setLastName(scanner.nextLine());

            System.out.println("What is the professor's Email?");
            professor.setEmail(scanner.nextLine());

            System.out.println("What is the professor's Phone?");
            professor.setPhone(scanner.nextLine());

            System.out.println("What is the professor's birthday [yyyy-MM-dd]?");
            String birthday = scanner.nextLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            professor.setBirthday(LocalDate.parse(birthday, dtf));

            System.out.println("Professor updated successfully");
        } else{
            System.out.println("Professor not found in the list");
        }
    }

    //display all professors

    public void display(){
        if(list.isEmpty()){
            System.out.println("There is no professor to display");
        }

        for(Professor prof : list){
            System.out.println(prof.toString());
        }

    }


}
