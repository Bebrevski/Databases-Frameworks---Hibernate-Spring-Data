package app.ccb.services;

import app.ccb.repositories.CardRepository;
import app.ccb.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CardServiceImpl implements CardService {

    private static final String CARDS_XML_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\18. WORKSHOP – MVC PROJECT SPRING MVC + SPRING DATA\\src\\main\\resources\\files\\xml\\cards.xml";

    private final CardRepository cardRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, FileUtil fileUtil) {
        this.cardRepository = cardRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean cardsAreImported() {
        return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return this.fileUtil.readFile(CARDS_XML_FILE_PATH);
    }

    @Override
    public String importCards() {
        // TODO : Implement Me
        return null;
    }
}
