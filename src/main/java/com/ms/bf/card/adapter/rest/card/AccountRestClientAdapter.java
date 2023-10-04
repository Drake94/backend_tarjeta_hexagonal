package com.ms.bf.card.adapter.rest.card;

import com.ms.bf.card.adapter.rest.card.model.account.AccountModel;
import com.ms.bf.card.adapter.rest.exception.BadRequestRestClientException;
import com.ms.bf.card.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.ms.bf.card.adapter.rest.exception.NotFoundRestClientException;
import com.ms.bf.card.adapter.rest.exception.TimeoutRestClientException;
import com.ms.bf.card.adapter.rest.handler.RestTemplateErrorHandler;
import com.ms.bf.card.application.port.out.AccountNumberRepository;
import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.property.AccountProperty;
import com.ms.bf.card.config.property.CardProperty;
import com.ms.bf.card.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;


@Repository
@Slf4j
public class AccountRestClientAdapter implements AccountNumberRepository {

    private final CardProperty property;

    private final RestTemplate restTemplate;



    public AccountRestClientAdapter(CardProperty property, RestTemplate restTemplate) {
        this.property = property;
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
    public Account getAccountNumber(Account account) {
        //AccountModel response = Optional.ofNullable(restTemplate.getForObject(property.getUrl() + "/account/" + account.getAccountNumber(), AccountModel.class))
        AccountModel response = Optional.ofNullable(restTemplate.getForObject(property.getUrl(property.getUrlAccount(),account.getAccountNumber()), AccountModel.class))
                .orElseThrow(() ->new EmptyOrNullBodyRestClientException(ErrorCode.ACCOUNT_NOT_FOUND));
        return response.toAccountDomain();

    }
}
