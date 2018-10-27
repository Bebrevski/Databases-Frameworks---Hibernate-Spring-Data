package Mankind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] studentInfo = reader.readLine().split(" ");
        String firstName = studentInfo[0];
        String lastName = studentInfo[1];
        String facultyNumber = studentInfo[2];

        try {
            Human student = new Student(firstName, lastName, facultyNumber);

            System.out.println(student.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        String[] workerInfo = reader.readLine().split("\\s+");
        firstName = workerInfo[0];
        lastName = workerInfo[1];
        double weekSalary = Double.parseDouble(workerInfo[2]);
        double workingHours = Double.parseDouble(workerInfo[3]);

        try {
            Human worker = new Worker(firstName, lastName, weekSalary, workingHours);

            System.out.println(worker.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
