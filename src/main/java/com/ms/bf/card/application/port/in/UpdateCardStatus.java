package com.ms.bf.card.application.port.in;

import com.ms.bf.card.domain.CardStatus;

import java.net.URISyntaxException;

public interface UpdateCardStatus {
    CardStatus UpdateStatus (CardStatus updateCard) throws URISyntaxException;
}
