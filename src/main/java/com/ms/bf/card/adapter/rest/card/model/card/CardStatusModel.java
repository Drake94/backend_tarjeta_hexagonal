package com.ms.bf.card.adapter.rest.card.model.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.domain.CardStatus;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardStatusModel {

    private static final String CARD_STATUS_PATTERN = "([2|3])";
    private static final String CARD_STATUS_ACTIVE = "2";
    private static final String CARD_STATUS_BLOCKED = "3";

    @JsonProperty("numero-tarjeta")
    @NotNull
    private String cardNumber;

    @JsonProperty("estado-tarjeta")
    @NotNull
    @Pattern(regexp = CARD_STATUS_PATTERN, message = "El estado de la tarjeta debe ser de activo o bloqueado")
    private String cardStatus;


    public CardStatus toCardStatusDomain() {
        return CardStatus.builder()
                .cardNumber(this.cardNumber)
                .cardStatus(this.cardStatus.equals(CARD_STATUS_BLOCKED)? CARD_STATUS_BLOCKED : CARD_STATUS_ACTIVE)
                .build();
    }

}
