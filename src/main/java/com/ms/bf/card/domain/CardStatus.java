package com.ms.bf.card.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
@Getter
public class CardStatus {
    String cardStatus;
    String cardNumber;


}
