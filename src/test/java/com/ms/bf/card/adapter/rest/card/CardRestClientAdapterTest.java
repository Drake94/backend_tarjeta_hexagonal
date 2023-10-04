package com.ms.bf.card.adapter.rest.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.bf.card.config.property.AccountProperty;
import com.ms.bf.card.domain.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@DisplayName("CardRestClientAdapterTest")
@Import(value = {AccountProperty.class})
@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(value={CardRestClientAdapter.class})
public class CardRestClientAdapterTest {


    @Autowired
    private CardRestClientAdapter adapter;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    MockRestServiceServer server;

    @Test
    @DisplayName("When card rest is success call")
    void getRestCardOk() {

        server.expect(requestTo("/api/v1/card"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("response body", MediaType.APPLICATION_JSON));

        Card response = adapter.getCard(Integer.parseInt("TEST"));
        assertEquals("response body", response);

        server.verify();
    }




}
