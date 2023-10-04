package com.ms.bf.card.adapter.controller.model.card;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.domain.Account;
import com.ms.bf.card.domain.CardStatus;
import com.ms.bf.card.domain.CreateCard;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardStatusRest {

    private static final String CARD_STATUS_PATTERN = "([2|3])";
    private static final String CARD_STATUS_ACTIVE = "2";
    private static final String CARD_STATUS_BLOCKED = "3";

    @JsonProperty("numero-tarjeta")
    @NotNull
    String cardNumber;

    @JsonProperty("estado-tarjeta")
    @NotNull
    @Pattern(regexp = CARD_STATUS_PATTERN, message = "El estado de la tarjeta debe ser de activo o bloqueado")
    String cardStatus;

    public static CardStatusRest toCardStatusRest(CardStatus cardStatus) {
        return Objects.nonNull(cardStatus)?
                CardStatusRest.builder()
                        .cardNumber(cardStatus.getCardNumber())
                        .cardStatus(cardStatus.getCardStatus())
                        .build() : null;
    }

    public CardStatus toCardStatusDomain() {
        return CardStatus.builder()
                .cardNumber(this.cardNumber)
                .cardStatus(this.cardStatus.equals(CARD_STATUS_BLOCKED)? CARD_STATUS_BLOCKED: CARD_STATUS_ACTIVE)
                .build();
    }

}
