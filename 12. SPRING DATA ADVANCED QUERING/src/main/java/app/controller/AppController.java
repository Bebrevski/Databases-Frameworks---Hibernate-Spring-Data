package app.controller;

import app.service.shampoo.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;


@Controller
public class AppController implements CommandLineRunner {

    private final ShampooService shampooService;

    public AppController(ShampooService shampooService) {
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        //Task 1
        selectShampoosBySize(scanner);

        //Task 2
    }

    //1.	Select Shampoos by Size
    private void selectShampoosBySize(Scanner scanner) {
        System.out.println("Enter size:");
        String inputSize = scanner.nextLine();

        this.shampooService.selectShampoosBySize(inputSize)
                .forEach(System.out::println);
    }
}
