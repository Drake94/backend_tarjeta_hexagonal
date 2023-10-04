package com.ms.bf.card.adapter.rest.card.model.card;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.adapter.rest.card.model.account.AccountModel;
import com.ms.bf.card.domain.Card;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardModel {

    private static final String CARD_NUMBER_PATTERN = "([0|1])";
    private static final String CARD_STATUS_PATTERN = "([2|3])";
    private static final String CARD_TYPE_PATTERN = "([4|5])";
    private static final int CARD_STATUS_ACTIVE = 2;
    private static final int CARD_STATUS_BLOCKED = 3;
    private static final int CARD_TYPE_TITULAR = 4;
    private static final int CARD_TYPE_ADDITIONAL = 5;


    private int account;

    private int cardNumber;

    private  int type;

    private int status;

    private String descriptionStatus;

    private String descriptionType;

    public int isTitular() {
        if (type == 4) {
            setType(CARD_TYPE_TITULAR);
            return CARD_TYPE_TITULAR;
        } else {
            setType(CARD_TYPE_ADDITIONAL);
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

    public Card toRest(Card card){
        return Card.builder()
                .account(account)
                .cardNumber(cardNumber)
                .status(status)
                .descriptionStatus(descriptionStatus())
                .type(type)
                .descriptionType(descriptionType)
                .build();
    }
    public Card toCardDomain() {
        return Card.builder()
                .account(this.account)
                .cardNumber(this.cardNumber)
                .status(this.status)
                .descriptionStatus(this.descriptionStatus)
                .type(this.type)
                .descriptionType(this.descriptionType)
                .build();

    }









}
