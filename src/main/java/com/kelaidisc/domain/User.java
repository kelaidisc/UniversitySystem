package com.kelaidisc.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class User {

    private static int id = -1;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String courses; //could be String[]


    //Constructor: ask from user to give firstName, lastName, email, phone, birthday

    public User() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first name: ");
        firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        email = scanner.nextLine();

        System.out.print("Enter phone: ");
        phone = scanner.nextLine();

        System.out.print("Enter date of birth: [yyyy-MM-dd] ");
        String dobString = scanner.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        birthday = LocalDate.parse(dobString, dtf);

        id++;
    }

    //Show all the details of the User(Use ID)
    public void printUser(int id){
        System.out.println("Id = " + id +
                "\nName = " + lastName + " " + firstName +
                "\nEmail = " + email +
                "\nPhone = " + phone +
                "\nBirthday = " + birthday);
    }

}
