package com.ms.bf.card.adapter.rest.card;

import com.ms.bf.card.adapter.controller.model.RestResponse;
import com.ms.bf.card.adapter.rest.card.model.card.CardStatusModel;
import com.ms.bf.card.adapter.rest.card.model.card.CreateCardModel;
import com.ms.bf.card.adapter.rest.exception.BadRequestRestClientException;
import com.ms.bf.card.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.ms.bf.card.adapter.rest.exception.NotFoundRestClientException;
import com.ms.bf.card.adapter.rest.exception.TimeoutRestClientException;
import com.ms.bf.card.adapter.rest.handler.RestTemplateErrorHandler;
import com.ms.bf.card.adapter.rest.card.model.card.CardModel;
import com.ms.bf.card.application.port.out.CardRepository;
import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.property.AccountProperty;
import com.ms.bf.card.config.property.FacadeProperty;
import com.ms.bf.card.domain.Card;
import com.ms.bf.card.domain.CardStatus;
import com.ms.bf.card.domain.CreateCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class CardRestClientAdapter implements CardRepository{
    private final FacadeProperty property;
    private final AccountProperty accountproperty;
    private final RestTemplate restTemplate;



    public CardRestClientAdapter(FacadeProperty property, AccountProperty accountproperty, RestTemplate restTemplate) {
        this.property = property;
        this.accountproperty = accountproperty;
        this.restTemplate = restTemplate;
        var errorHandler = new RestTemplateErrorHandler(
                Map.of(
                        HttpStatus.NOT_FOUND, new NotFoundRestClientException(ErrorCode.ACCOUNT_NOT_FOUND),
                        HttpStatus.REQUEST_TIMEOUT, new TimeoutRestClientException(ErrorCode.CARD_TIMEOUT),
                        HttpStatus.BAD_REQUEST, new BadRequestRestClientException(ErrorCode.CARD_BAD_REQUEST)
                )
        );
        this.restTemplate.setErrorHandler(errorHandler);
    }

    @Override
    public Card getCard( String accountNumber) {
        log.info("Servicio obtener tarjeta, buscar: [{}]" ,property.getUrl(property.getUrlBase(), property.getUrlInternal(), accountNumber));
        log.debug("este mensaje no debe aparece en el modo develop");
        CardModel response = Optional.ofNullable(restTemplate.getForObject(property.getUrl(property.getUrlBase(), property.getUrlInternal(),accountNumber),CardModel.class))
                .orElseThrow(()-> new EmptyOrNullBodyRestClientException(ErrorCode.ACCOUNT_NOT_FOUND));
        log.info("Servicio obtener tarjeta, buscar: [{}]" ,property.getUrl(property.getUrlBase(), property.getUrlInternal(), accountNumber));
        log.info("Respuesta obtenida desde el servicio obtener tarjeta data: [{}]", response);
        return response.toCardDomain();
    }

    @Override
    public Card create(Card card) {
        log.info("Servicio crear tarjeta, lo conecta a: [{}]" ,property.getUrlCreates(property.getUrlCreate()));
        log.info(" a crear : [{}]" ,card);
        CardModel response = Optional.ofNullable(restTemplate.postForObject(property.getUrlCreates(property.getUrlCreate()),card, CardModel.class))
                .orElseThrow(()-> new EmptyOrNullBodyRestClientException(ErrorCode.ACCOUNT_NOT_FOUND));
        log.info("popo[{}]", response);
        return response.toRest(card);
    }

    @Override
    public CardStatus UpdateStatus(CardStatus updateCard) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CardStatusModel objClient = new CardStatusModel();
        HttpEntity<CardStatusModel> httpEntity = new HttpEntity<CardStatusModel>(objClient, headers);
        RestTemplate restTemplate = new RestTemplate();

        URI uri = new URI(property.getUpdateUrl(property.getUrlBase(), property.getUrlUpdate()));
        uri = uri.resolve(updateCard.getCardNumber());

        ResponseEntity<CardStatusModel> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity,
                CardStatusModel.class);
        log.info("popo" + responseEntity);
        return responseEntity.getBody().toCardStatusDomain();
    }

}
