package com.ms.bf.card.application.port.out;

import com.ms.bf.card.domain.Card;
import com.ms.bf.card.domain.CardStatus;
import com.ms.bf.card.domain.CreateCard;

import java.net.URISyntaxException;

public interface CardRepository {

    Card getCard(String accountNumber);

    //CreateCard createCard(CreateCard card);

    Card create(Card card);


    CardStatus UpdateStatus (CardStatus updateCard) throws URISyntaxException;
}
