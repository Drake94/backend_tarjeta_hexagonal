package com.ms.bf.card.application.port.in;
import com.ms.bf.card.domain.Card;

import java.util.concurrent.ExecutionException;

public interface GetCard {

    Card getCard(String accountNumber)throws ExecutionException, InterruptedException;

}
