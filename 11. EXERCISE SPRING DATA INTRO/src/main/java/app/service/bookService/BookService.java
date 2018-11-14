package app.service.bookService;

import java.io.IOException;
import java.util.List;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();
}
