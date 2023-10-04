package com.ms.bf.card.application.usecase;

import com.ms.bf.card.application.port.in.GetCard;
import com.ms.bf.card.application.port.out.AccountNumberRepository;
import com.ms.bf.card.application.port.out.CardRepository;
import com.ms.bf.card.domain.Card;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Component
@AllArgsConstructor
@Slf4j
public class GetCardUseCase implements GetCard {

    private final AccountNumberRepository accountNumberRepository;
    private final CardRepository cardRepository;
    private final Executor executor;

    @Override
    public Card getCard(String accountNumber) throws ExecutionException, InterruptedException {
        Card card = cardRepository.getCard(accountNumber);
        log.info("tarjeta obtenida [{}]", card);
        log.info(String.valueOf(accountNumber));
        return card;

    }

    /*private CompletableFuture<Account> getAccountNumber(Account account) {
        return CompletableFuture.supplyAsync(() -> accountNumberRepository.getAccountNumber(account), executor)
                .exceptionally(exception -> {
                    log.error("Error al obtener el numero de cuenta", exception);
                    return null;
                });

    }*/

    }
