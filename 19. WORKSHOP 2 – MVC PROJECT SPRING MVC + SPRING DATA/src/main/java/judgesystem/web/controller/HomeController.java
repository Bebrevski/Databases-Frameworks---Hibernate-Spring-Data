package judgesystem.web.controller;

import judgesystem.service.CategoryService;
import judgesystem.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class HomeController implements CommandLineRunner {

    private static final String CATEGORIES_JSON_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\categories.json";
    private static final String STRATEGIES_JSON_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\strategies.json";

    private final CategoryService categoryService;
    private final StrategyService strategyService;

    @Autowired
    public HomeController(CategoryService categoryService, StrategyService strategyService) {
        this.categoryService = categoryService;
        this.strategyService = strategyService;
    }

    @Override
    public void run(String... args) throws Exception {
        //this.importCategories();
        //this.importStrategies();
    }

    private void importStrategies() throws IOException {
        System.out.println(this.strategyService.importStrategies(STRATEGIES_JSON_FILE_PATH));
    }

    private void importCategories() throws IOException {
        System.out.println(this.categoryService.importCategories(CATEGORIES_JSON_FILE_PATH));
    }
}
