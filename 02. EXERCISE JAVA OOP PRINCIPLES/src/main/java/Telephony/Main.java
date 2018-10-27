package Telephony;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Smartphone phone = new Smartphone();

        String[] numbers = scanner.nextLine().split(" ");

        for (String number : numbers) {
            try {
                System.out.println(phone.call(number));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

        String[] urls = scanner.nextLine().split(" ");

        for (String url : urls) {
            try {
                System.out.println(phone.browse(url));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
