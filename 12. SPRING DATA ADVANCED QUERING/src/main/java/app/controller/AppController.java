package app.controller;

import app.service.shampoo.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class AppController implements CommandLineRunner {

    private final ShampooService shampooService;
    private final Scanner scanner;

    public AppController(ShampooService shampooService, Scanner scanner) {
        this.shampooService = shampooService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
