package app;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class Engine implements Runnable {

    private EntityManager entityManager;
    private Scanner scanner;

    public Engine(EntityManager entityManager, Scanner scanner) {
        this.entityManager = entityManager;
        this.scanner = scanner;
    }

    @Override
    public void run() {

    }
}
