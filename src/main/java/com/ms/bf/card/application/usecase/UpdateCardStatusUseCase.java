package com.ms.bf.card.application.usecase;

import com.ms.bf.card.application.port.in.UpdateCardStatus;
import com.ms.bf.card.application.port.out.CardRepository;
import com.ms.bf.card.domain.Card;
import com.ms.bf.card.domain.CardStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@AllArgsConstructor
@Slf4j
public class UpdateCardStatusUseCase implements UpdateCardStatus {

    private final CardRepository cardRepository;
    @Override
    public CardStatus UpdateStatus(CardStatus updateCard) throws URISyntaxException {
        CardStatus card = cardRepository.UpdateStatus(updateCard);
        log.info("tarjeta modificada [{}]", card);
        return updateCard;
    }


}

