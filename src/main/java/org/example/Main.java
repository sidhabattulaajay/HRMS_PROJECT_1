package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner sc= new Scanner(System.in);
        while(true){


            System.out.println("\n===== HRMS =====");
            System.out.println("1.Create new Organization");
            System.out.println("2.Login to Organization");
            System.out.println(" 3.Exit");
            System.out.println("Enter your Choice");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:
                    Organization.createOrganization(sc);
                    break;

                case 2:
                    Login.showLoginMenu(sc);
                    break;

                case 3:
                    System.out.println("Exiting HRMS...");
                    sc.close();
                    return;

                default:System.out.println("Invalid Choice");

            }

        }
    }
}
