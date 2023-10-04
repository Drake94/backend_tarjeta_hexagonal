package com.ms.bf.card.adapter.rest.card.model.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.adapter.rest.card.model.account.AccountModel;
import com.ms.bf.card.domain.CreateCard;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCardModel {

    private static final String CARD_TYPE_PATTERN = "([4|5])";
    private static final String CARD_TYPE_TITULAR = "4";
    private static final String CARD_TYPE_ADDITIONAL = "5";

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @JsonProperty("numero-cuenta")
    private AccountModel account;

    @JsonProperty("tipo-tarjeta")
    @NotNull
    @Pattern(regexp = CARD_TYPE_PATTERN, message = "El tipo de tarjeta debe ser titular o adicional")
    private String cardType;

    public CreateCard toCreateCardDomain() {
        return CreateCard.builder()
                .account(this.account.toAccountDomain())
                .cardType(this.cardType.equals(CARD_TYPE_TITULAR)? CARD_TYPE_TITULAR : CARD_TYPE_ADDITIONAL)
                .build();
    }
}
