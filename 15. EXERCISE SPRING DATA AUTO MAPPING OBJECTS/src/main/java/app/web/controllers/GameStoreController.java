package app.web.controllers;

import app.domain.dtos.UserRegisterDTO;
import app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {

    private final Scanner scanner;
    private final UserService userService;

    @Autowired
    public GameStoreController(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String input = this.scanner.nextLine();

            String[] inputParams = input.split("\\|");

            switch (inputParams[0]) {
                case "RegisterUser":
                    UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                            inputParams[1],
                            inputParams[2],
                            inputParams[3],
                            inputParams[4]
                    );

                    System.out.println(this.userService.registerUser(userRegisterDTO));
                    break;
                case "LoginUser":
                    break;
                case "LogoutUser":
                    break;
            }
        }
    }
}
