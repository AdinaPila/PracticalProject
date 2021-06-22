package database.prisonsmanagement;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {

    public static Integer scannerOption(){
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        try {
            option = scanner.nextInt();
        }catch (InputMismatchException e){
            System.out.println(e);
            System.out.println("Invalide value. Try again");
            scannerOption();
        }

        return option;
    }

    public static String scannerOptionString(){
        Scanner scanner = new Scanner(System.in);
        String option = " ";
        try {
            option = scanner.next();
        }catch (InputMismatchException e){
            System.out.println(e);
            System.out.println("Invalide value. Try again");
            scannerOptionString();
        }
        return option;
    }
}
