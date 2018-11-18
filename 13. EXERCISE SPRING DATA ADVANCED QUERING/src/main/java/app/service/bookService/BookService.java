package app.service.bookService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    List<String> getAllBooksByAgeRestriction(String ageRestriction);

    List<String> getAllGoldenBooks();

    List<String> getAllBooksByPrice();

    List<String> getAllNotReleasedBooks(String year);

    List<String> getAllBooksBeforeDate(String year);
}
