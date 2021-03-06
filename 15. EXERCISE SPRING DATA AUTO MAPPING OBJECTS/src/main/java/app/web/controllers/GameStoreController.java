package app.web.controllers;

import app.domain.dtos.GameAddDTO;
import app.domain.dtos.UserLoginDTO;
import app.domain.dtos.UserLogoutDTO;
import app.domain.dtos.UserRegisterDTO;
import app.service.game.GameService;
import app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {

    private String loggedInUser;

    private final Scanner scanner;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(Scanner scanner, UserService userService, GameService gameService) {
        this.scanner = scanner;
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String input = this.scanner.nextLine();

            if ("end".equals(input.toLowerCase())) {
                break;
            }

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
                    if (this.loggedInUser == null) {
                        UserLoginDTO userLoginDTO = new UserLoginDTO(
                                inputParams[1],
                                inputParams[2]
                        );
                        String loginResult = this.userService.loginUser(userLoginDTO);

                        if (loginResult.contains("Successfully logged in")) {
                            System.out.println(loginResult);
                            this.loggedInUser = userLoginDTO.getEmail();
                        }
                    } else {
                        System.out.println("Session is taken!");
                    }
                    break;
                case "LogoutUser":
                    if (this.loggedInUser == null) {
                        System.out.println("Cannot log out. No user was logged in.");
                    } else {
                        String logoutResult = this.userService.logoutUser(new UserLogoutDTO(this.loggedInUser));
                        System.out.println(logoutResult);

                        this.loggedInUser = null;
                    }
                    break;

                case "AddGame":
                    if (this.loggedInUser != null && this.userService.isAdmin(this.loggedInUser)) {
                        System.out.println();
                        GameAddDTO gameAddDTO = new GameAddDTO(
                                inputParams[1],
                                new BigDecimal(inputParams[2]),
                                Double.parseDouble(inputParams[3]),
                                inputParams[4],
                                inputParams[5],
                                inputParams[6]
                        );

                        System.out.println(this.gameService.addGame(gameAddDTO));
                    } else {
                        System.out.println("Cannot log out. No user was logged in.");
                    }
                    break;
            }
        }
    }
}
