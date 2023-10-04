package com.ms.bf.card.adapter.controller.model.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.domain.Account;
import com.ms.bf.card.domain.CreateCard;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCardRest {

    private static final String CARD_TYPE_PATTERN = "([4|5])";
    private static final String CARD_TYPE_TITULAR = "4";
    private static final String CARD_TYPE_ADDITIONAL = "5";

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @JsonProperty("cuenta")
    AccountRest account;

    @JsonProperty("tipo-tarjeta")
    @NotNull
    @Pattern(regexp = CARD_TYPE_PATTERN, message = "El tipo de tarjeta debe ser titular o adicional")
    String cardType;

    public static CreateCardRest toCreateCardRest(CreateCard createCard) {
        return Objects.nonNull(createCard)?
                CreateCardRest.builder()
                        .account(AccountRest.toAccountRest(createCard.getAccount()))
                        .cardType(createCard.getCardType())
                        .build() : null;
    }

    public CreateCard toCreateCardDomain() {
        return CreateCard.builder()
                .account(Account.builder()
                        .accountNumber(this.getAccount().getAccountNumber())
                        .build())
                .cardType(this.cardType.equals(CARD_TYPE_TITULAR)? CARD_TYPE_TITULAR : CARD_TYPE_ADDITIONAL)
                .build() ;
    }
}
