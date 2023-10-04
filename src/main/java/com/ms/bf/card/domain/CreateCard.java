package com.ms.bf.card.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class CreateCard {
    Account account;
    String cardType;
}
