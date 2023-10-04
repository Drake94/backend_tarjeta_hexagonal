package com.ms.bf.card.mocks;

import com.ms.bf.card.adapter.controller.model.RestResponse;
import com.ms.bf.card.adapter.controller.model.card.AccountRest;
import com.ms.bf.card.adapter.controller.model.card.CardRest;
import com.ms.bf.card.domain.Account;
import com.ms.bf.card.domain.Card;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public final class CardMock {
    public static final String STRING_VALUE = "test";

    public static CardRest ToCardRest(Card card) {
        return CardRest.builder()
                .account(AccountRest.toAccountRest(card.getAccount()))
                .cardType(card.getCardType())
                .cardNumber(card.getCardNumber())
                .cardStatus(card.getCardStatus())
                .build();
    }
    public static Card toCardDomain() {
        return Card.builder()
                .cardType(STRING_VALUE)
                .cardNumber(STRING_VALUE)
                .cardStatus(STRING_VALUE)
                .account(Account.builder()
                        .accountNumber(STRING_VALUE)
                        .build())
                .build();
    }

}
