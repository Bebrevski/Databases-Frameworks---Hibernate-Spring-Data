package app.service.bookService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    Set<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookAfter();

    List<String> getAllBooksFromGivenAuthor();
}
