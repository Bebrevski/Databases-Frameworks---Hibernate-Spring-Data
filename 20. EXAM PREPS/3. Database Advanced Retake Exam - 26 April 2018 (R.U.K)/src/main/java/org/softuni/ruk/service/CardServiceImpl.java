package org.softuni.ruk.service;

import org.softuni.ruk.repository.CardRepository;
import org.softuni.ruk.service.interfaces.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardsService {

    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public String importCards(String cardsFileContent) {
        return null;
    }
}
