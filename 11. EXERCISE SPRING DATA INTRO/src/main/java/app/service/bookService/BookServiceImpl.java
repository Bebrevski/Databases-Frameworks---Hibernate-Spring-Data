package app.service.bookService;

import app.domain.entities.*;
import app.repository.AuthorRepository;
import app.repository.BookRepository;
import app.repository.CategoryRepository;
import app.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private static final String BOOK_FILE_PATH =
            "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\11. EXERCISE SPRING DATA INTRO\\src\\main\\resources\\files\\books.txt";

    private final FileUtil fileUtil;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(FileUtil fileUtil, BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.fileUtil = fileUtil;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] lines = this.fileUtil.getFileContent(BOOK_FILE_PATH);
        for (String line : lines) {
            String[] bookTokens = line.split("\\s+");

            Book book = new Book();

            Author author = this.getRandomAuthor();
            book.setAuthor(author);

            EditionType editionType = EditionType.values()[Integer.parseInt(bookTokens[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(
                    bookTokens[1],
                    DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(bookTokens[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(bookTokens[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookTokens[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < bookTokens.length; i++) {
                title.append(bookTokens[i]).append(" ");
            }
            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository
                .findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1) + 1);

        return this.authorRepository.findById((long) randomId).orElse(null);
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1) + 1);

        return this.categoryRepository.findById((long) randomId).orElse(null);
    }

    private Set<Category> getRandomCategories(){
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();
            categories.add(category);
        }

        return categories;
    }
}
