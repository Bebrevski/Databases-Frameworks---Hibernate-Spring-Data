package app.controller;

import app.service.authorService.AuthorService;
import app.service.bookService.BookService;
import app.service.categoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class BookShopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookShopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();

        //Task 1

        //this.bookService.getAllBooksTitlesAfter()
        //.forEach(System.out::println);

        //Task 2

        //this.bookService.getAllAuthorsWithBookAfter()
        //.forEach(System.out::println);

        //Task 3

        //this.authorService.getAuthorByCountOfBooks()
        //.forEach(System.out::println);

        //Task 4

        //this.bookService.getAllBooksFromGivenAuthor()
                //.forEach(System.out::println);
    }
}
