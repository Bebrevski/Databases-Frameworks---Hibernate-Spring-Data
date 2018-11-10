package app;

import app.gringottsDatabase.WizardDeposit;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Scanner;

public class Engine implements Runnable {

    private EntityManager manager;
    private Scanner scanner;

    public Engine(EntityManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }


    public void run() {
        // this.createGringottsDatabse(); // Task 1

        createSalesDatabаse();            // Task 2
    }

    //1.	Gringotts Database
    private void createGringottsDatabse() {
        WizardDeposit deposit = new WizardDeposit();

        deposit.setAge(29);
        deposit.setFirstName("Bai Pesho");
        deposit.setLastName("Motokarista");
        deposit.setDepositStartDate(new Date());

        this.manager.getTransaction().begin();

        this.manager.persist(deposit);

        this.manager.getTransaction().commit();
    }

    //2.	Sales Database
    private void createSalesDatabаse() {

    }
}
