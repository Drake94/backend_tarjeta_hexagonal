package com.ms.bf.card.adapter.controller.model.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.domain.Account;
import com.ms.bf.card.domain.Card;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardRest {

    private static final String CARD_NUMBER_PATTERN = "([0|1])";
    private static final String CARD_STATUS_PATTERN = "([2|3])";
    private static final String CARD_TYPE_PATTERN = "([4|5])";
    private static final int CARD_STATUS_ACTIVE = 2;
    private static final int CARD_STATUS_BLOCKED = 3;
    private static final int CARD_TYPE_TITULAR = 4;
    private static final int CARD_TYPE_ADDITIONAL = 5;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @JsonProperty("numero-cuenta")
    int account;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @JsonProperty("numero-tarjeta")
    int cardNumber;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @JsonProperty("tipo-tarjeta")
    int type;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @JsonProperty("estado-tarjeta")
    int status;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonProperty("descripcion-estado")
    String descriptionStatus;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonProperty("descripcion-tipo")
    String descriptionType;

    public int isTitular() {
        if (type == 4) {

            return CARD_TYPE_TITULAR;
        } else {

            return CARD_TYPE_ADDITIONAL;
        }

    }

    public int isActive() {
        if (status == 3) {
            return CARD_STATUS_BLOCKED;
        } else {
            return CARD_STATUS_ACTIVE;
        }
    }


        public String descriptionStatus(){
        if(isActive()==2){
            setDescriptionStatus("Activo");
        }else{
            setDescriptionStatus("Bloqueado");
        }
        return descriptionStatus;

    };
    public String descriptionType(){
        if(isTitular()==4){
            setDescriptionType("Titular");
        }else{
            setDescriptionType("Adicional");
        }
        return descriptionType;

    };

    public static CardRest toCardRest(Card card) {
        return Objects.nonNull(card)?
                CardRest.builder()
                        .account(card.getAccount())
                        .cardNumber(card.getCardNumber())
                        .type(card.getType())
                        .descriptionType(card.getDescriptionType())
                        .status(card.getStatus())
                        .descriptionStatus(card.getDescriptionStatus())
                        .build() : null;
    }

    public Card toCardDomain() {
        return Card.builder()
                        .cardNumber(this.cardNumber)
                        .account(this.account)
                        .type(this.type)
                        .descriptionType(this.descriptionType)
                        .status(this.status)
                        .descriptionStatus(this.descriptionStatus)
                        .build() ;
    }

}
