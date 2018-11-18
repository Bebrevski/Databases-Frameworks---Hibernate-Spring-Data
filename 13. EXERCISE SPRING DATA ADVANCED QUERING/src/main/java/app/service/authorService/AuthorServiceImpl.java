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

    private final static String AUTHORS_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\13. EXERCISE SPRING DATA ADVANCED QUERING\\src\\main\\resources\\files\\authors.txt";

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

        String[] authorFileContent = this.fileUtil.getFileContent(AUTHORS_FILE_PATH);
        for (String line : authorFileContent) {
            String[] names = line.split("\\s+");

            Author author = new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public List<String> getAllAuthorsFirstNameStartsWith(String letter) {
        String wildCard = "%" + letter;

        //Invoke of Custom Method in AuthorRepository

        return this.authorRepository.findAllByFirstNameEndsWithCustomQuery(wildCard)
                .stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .collect(Collectors.toList());

        //Invoke of Built in Method in Author Repository

//       return this.authorRepository.findAllByFirstNameEndsWith(letter)
//               .stream()
//               .map(a -> a.getFirstName() + " " + a.getLastName())
//               .collect(Collectors.toList());
    }

    @Override
    public List<String> getTotalNumberOfCopiesByAuthor() {
        return this.authorRepository.getTotalNumberOfBookCopiesByAuthor()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}
