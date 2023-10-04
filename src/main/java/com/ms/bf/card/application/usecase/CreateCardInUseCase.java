package com.ms.bf.card.application.usecase;

import com.ms.bf.card.application.port.in.CreateCardIn;
import com.ms.bf.card.application.port.in.CreateIn;
import com.ms.bf.card.application.port.in.GetCard;
import com.ms.bf.card.application.port.out.CardRepository;
import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.CardException;
import com.ms.bf.card.domain.Card;
import com.ms.bf.card.domain.CardType;
import com.ms.bf.card.domain.CreateCard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Component
@AllArgsConstructor
@Slf4j
public class CreateCardInUseCase implements CreateIn {

    private final CardRepository createCardRepository;
    private final GetCard getCard;
    private final Executor executor;
    private final CardType type ;


    @Override
    public Card create(Card card) {
        return createCardRepository.create(card);
        // create = createCardRepository.create(card);
        //if (card.getAccount() != 0 && card.getCardNumber() != 0 && card.getCardStatus() != 0 && card.getDescriptionStatus() != null && card.getCardType() != 0 && card.getDescriptionType() != null  ) {
        //    return create;
        //} throw new CardException(ErrorCode.CARD_INVALID_REQUEST);

    }

/*
    private CompletableFuture<Boolean> ifCardExists(Card card) {
        return CompletableFuture.supplyAsync(() -> {
            Card existingCard = null;
            try {
                existingCard = getCard.getCard(card.getCardNumber());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return existingCard != null;
        }, executor).exceptionally(exception -> {
            log.error("Ocurrió un error al obtener la información de la tarjeta", exception);
            return false;
        });
    }
*/

}

