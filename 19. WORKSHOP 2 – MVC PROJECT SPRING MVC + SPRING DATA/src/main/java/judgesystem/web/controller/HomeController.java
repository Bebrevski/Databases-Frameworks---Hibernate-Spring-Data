package judgesystem.web.controller;

import judgesystem.service.CategoryService;
import judgesystem.service.ContestService;
import judgesystem.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class HomeController implements CommandLineRunner {

    private static final String CATEGORIES_JSON_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\categories.json";
    private static final String STRATEGIES_JSON_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\strategies.json";
    private static final String CONTESTS_JSON_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\contests.json";

    private final CategoryService categoryService;
    private final StrategyService strategyService;
    private final ContestService contestService;

    @Autowired
    public HomeController(CategoryService categoryService, StrategyService strategyService, ContestService contestService) {
        this.categoryService = categoryService;
        this.strategyService = strategyService;
        this.contestService = contestService;
    }

    @Override
    public void run(String... args) throws Exception {
        //this.importCategories();
        //this.importStrategies();
        this.importContests();
    }

    private void importContests() throws IOException {
        System.out.println(this.contestService.importContests(CONTESTS_JSON_FILE_PATH));
    }

    private void importStrategies() throws IOException {
        System.out.println(this.strategyService.importStrategies(STRATEGIES_JSON_FILE_PATH));
    }

    private void importCategories() throws IOException {
        System.out.println(this.categoryService.importCategories(CATEGORIES_JSON_FILE_PATH));
    }
}
