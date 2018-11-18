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

    private static final String SOME_TEXT = "Enter problem number between 1 and 11 inclusive or 'end' to shut down the app";

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

        System.out.println(SOME_TEXT);

        String line;
        while (!"end".equals(line = this.scanner.nextLine())) {

            int number = Integer.parseInt(line);
            switch (number) {

                case 1:

                    System.out.println("Enter age restriction (case insensitive):");
                    try {
                        booksTitlesByAgeRestriction();
                    } catch (Exception ex) {
                        System.out.println("Invalid age restriction");
                    }

                    break;

                case 2:

                    goldenBooks();
                    break;

                case 3:

                    getAllBooksByPrice();
                    break;

                case 4:

                    System.out.println("Enter date in format 'yyyy'");
                    try {
                        getAllNotReleasedBooks();
                    } catch (Exception ex) {
                        System.out.println("Invalid date");
                    }
                    break;

                case 5:

                    System.out.println("Enter date in format 'dd-MM-yyyy'");
                    try {
                        getAllBooksReleasedBeforeDate();
                    } catch (Exception ex) {
                        System.out.println("Invalid date");
                    }
                    break;

                case 6:

                    System.out.println("Enter the pattern");
                    authorsSearch();
                    break;

                case 7:

                    System.out.println("Enter the pattern");
                    booksSearch();
                    break;

                case 8:

                    System.out.println("Enter the pattern");
                    bookTitlesSearch();
                    break;

                case 9:

                    System.out.println("Enter the min title length");
                    try {
                        countBooks();
                    } catch (Exception ex){
                        System.out.println("You should enter a number");
                    }
                    break;

                case 10:

                    totalBookCopies();
                    break;

                case 11:

                    System.out.println("Enter title");
                    try{
                        reducedBook();
                    } catch (Exception ex){
                        System.out.println("Invalid title");
                    }
                    break;

                default:
                    System.out.println("Invalid problem number");
                    break;
            }
            System.out.println(SOME_TEXT);
        }
    }

    //1.	Books Titles by Age Restriction
    private void booksTitlesByAgeRestriction() throws IllegalStateException {
        String ageRestriction = scanner.nextLine();

        this.bookService.getAllBooksByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    //2.	Golden Books
    private void goldenBooks() {
        this.bookService.getAllGoldenBooks()
                .forEach(System.out::println);
    }

    //3.	Books by Price
    private void getAllBooksByPrice() {
        this.bookService.getAllBooksByPrice()
                .forEach(System.out::println);
    }

    //4.	Not Released Books
    private void getAllNotReleasedBooks() {
        String year = this.scanner.nextLine();

        this.bookService.getAllNotReleasedBooks(year)
                .forEach(System.out::println);
    }

    //5.	Books Released Before Date
    private void getAllBooksReleasedBeforeDate() {
        String year = this.scanner.nextLine();

        this.bookService.getAllBooksBeforeDate(year)
                .forEach(System.out::println);
    }

    //6.	Authors Search
    private void authorsSearch() {
        String firstNameEndsWithLetter = this.scanner.nextLine();

        this.authorService.getAllAuthorsFirstNameStartsWith(firstNameEndsWithLetter)
                .forEach(System.out::println);
    }

    //7.	Books Search
    private void booksSearch() {
        String pattern = this.scanner.nextLine();

        this.bookService.getAllBooksByGivenPattern(pattern)
                .forEach(System.out::println);
    }

    //8.	Book Titles Search
    private void bookTitlesSearch() {
        String pattern = this.scanner.nextLine();

        this.bookService.getAllBooksWhereAuthorNameFitsThePattern(pattern)
                .forEach(System.out::println);
    }

    //9.	Count Books
    private void countBooks() {
        int length = Integer.parseInt(this.scanner.nextLine());

        System.out.println(this.bookService.getNumberOfBooksWithTitleLongerThan(length));
    }

    //10.	Total Book Copies
    private void totalBookCopies() {
        this.authorService.getTotalNumberOfCopiesByAuthor()
                .forEach(System.out::println);
    }

    //11.	Reduced Book
    private void reducedBook(){
        String inputTitle = this.scanner.nextLine();

        System.out.println(this.bookService.getBookByGivenTitle(inputTitle));
    }

    private void seedDatabase() throws IOException {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
    }
}
