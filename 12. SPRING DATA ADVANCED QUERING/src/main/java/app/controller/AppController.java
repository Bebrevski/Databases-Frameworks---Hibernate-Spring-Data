package app.controller;

import app.service.ingredients.IngredientService;
import app.service.shampoos.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Controller
public class AppController implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;
    private final Scanner scanner;

    public AppController(ShampooService shampooService, IngredientService ingredientService, Scanner scanner) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {

        //Task 1
        //selectShampoosBySize();

        //Task 2
        //selectShampoosBySizeOrLabel();

        //Task 3
        //selectShampoosByPrice();

        //Task 4
        selectIngredientsByName();

        //Task 7
        //selectShampoosByIngredients();
    }

    //1.	Select Shampoos by Size
    private void selectShampoosBySize() {
        System.out.println("Enter size:");
        String inputSize = scanner.nextLine();

        this.shampooService.selectShampoosBySize(inputSize)
                .forEach(System.out::println);
    }

    //2.	Select Shampoos by Size or Label
    private void selectShampoosBySizeOrLabel() {
        String size;
        Long labelId;

        String line1 = scanner.nextLine();
        String line2 = scanner.nextLine();
        try {
            labelId = Long.parseLong(line1);
            size = line2;
        } catch (NumberFormatException ex) {
            labelId = Long.parseLong(line2);
            size = line1;
        }

        this.shampooService.getShampoosByLabelOrSize(labelId, size)
                .forEach(System.out::println);
    }

    //3.	Select Shampoos by Price
    private void selectShampoosByPrice(){
        BigDecimal minPrice = new BigDecimal(scanner.nextLine());

        this.shampooService.selectShampoosByPrice(minPrice)
                .forEach(System.out::println);
    }

    //4.	Select Ingredients by Name
    private void selectIngredientsByName(){
        String letter = scanner.nextLine();

        this.ingredientService.selectIngredientsByName(letter)
                .forEach(System.out::println);
    }

    //7.	Select Shampoos by Ingredients
    private void selectShampoosByIngredients() {
        List<String> lines = new ArrayList<>();

        String line;
        while (!"".equals(line = scanner.nextLine())) {
            lines.add(line);
        }

        this.shampooService.selectShampoosByIngredients(lines)
                .forEach(System.out::println);
    }
}
