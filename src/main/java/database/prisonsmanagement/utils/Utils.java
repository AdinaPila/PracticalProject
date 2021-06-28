package database.prisonsmanagement.utils;

import database.prisonsmanagement.entities.InmatesEntity;
import database.prisonsmanagement.entities.UsersEntity;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static Integer scannerOption(){
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        try {
            option = scanner.nextInt();
        }catch (InputMismatchException e){
            System.out.println(e);
            System.out.println("Invalid value. Try again");
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
            System.out.println("Invalid value. Try again");
            scannerOptionString();
        }
        return option;
    }

    public static boolean isCNPValid(String cnp) {
        Pattern pattern = Pattern.compile("^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$");
        Matcher matcher = pattern.matcher(cnp);
//        return matcher.matches();
        return true;
    }

    public static void setCnp(Object object, String cnp) {
        if (object instanceof InmatesEntity) {
            if (Utils.isCNPValid(cnp)) {
                ((InmatesEntity) object).setCnpInmate(cnp);
            } else {
                System.out.println("CNP is not valid. Try again");
                cnp = Utils.scannerOptionString();
                setCnp(object,cnp);
            }

        } else if(object instanceof UsersEntity){
            if (Utils.isCNPValid(cnp)) {
                ((UsersEntity) object).setCnp(cnp);
            } else {
                System.out.println("CNP is not valid. Try again");
                cnp = Utils.scannerOptionString();
                setCnp(object,cnp);
            }
        }
    }


}
