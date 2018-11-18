package app.controller;

import app.service.authorService.AuthorService;
import app.service.bookService.BookService;
import app.service.categoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Scanner;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final Scanner scanner;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService, Scanner scanner) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... strings) throws Exception {

        //Seeding Database With Data
        //seedDatabase();

        //Task 1
        //booksTitlesByAgeRestriction();

        //Task 2
        //goldenBooks();

        //Task 3
        //getAllBooksByPrice();

        //Task 4
        //getAllNotReleasedBooks();

        //Task 5
        //getAllBooksReleasedBeforeDate();

        //Task 6
        authorsSearch();
    }

    private void seedDatabase() throws IOException {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
    }

    //1.	Books Titles by Age Restriction
    private void booksTitlesByAgeRestriction() {
        String ageRestriction = scanner.nextLine();

        this.bookService.getAllBooksByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    //2.	Golden Books
    private void goldenBooks(){
        this.bookService.getAllGoldenBooks()
                .forEach(System.out::println);
    }

    //3.	Books by Price
    private void getAllBooksByPrice(){
        this.bookService.getAllBooksByPrice()
                .forEach(System.out::println);
    }

    //4.	Not Released Books
    private void getAllNotReleasedBooks(){
        String year = this.scanner.nextLine();

        this.bookService.getAllNotReleasedBooks(year)
                .forEach(System.out::println);
    }

    //5.	Books Released Before Date
    private void getAllBooksReleasedBeforeDate(){
        String year = this.scanner.nextLine();

        this.bookService.getAllBooksBeforeDate(year)
                .forEach(System.out::println);
    }

    //6.	Authors Search
    private void authorsSearch(){
        String firstNameEndsWithLetter = this.scanner.nextLine();

        this.authorService.getAllAuthorsFirstNameStartsWith(firstNameEndsWithLetter)
                .forEach(System.out::println);
    }
}
