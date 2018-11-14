package app.service.authorService;

import app.domain.entities.Author;
import app.repository.AuthorRepository;
import app.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String AUTHORS_FILE_PATH =
            "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\11. EXERCISE SPRING DATA INTRO\\src\\main\\resources\\files\\authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] lines = fileUtil.getFileContent(AUTHORS_FILE_PATH);
        for (String line : lines) {
            String[] names = line.split("\\s+");

            Author author = new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public List<String> getAuthorByCountOfBooks() {
        return this.authorRepository.findAuthorsByOrderByBooksDesc().stream()
                .map(a -> String.format("%s %s %d"
                        , a.getFirstName()
                        , a.getLastName()
                        , a.getBooks().size()))
                .collect(Collectors.toList());
    }
}
