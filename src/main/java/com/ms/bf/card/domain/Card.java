package com.ms.bf.card.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class Card{
    int account;
    int cardNumber;
    int status;
    String descriptionStatus;
    int type;
    String descriptionType;
}
