package com.ms.bf.card.adapter.controller.card;

import com.ms.bf.card.application.port.in.GetCard;
import com.ms.bf.card.mocks.CardMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@DisplayName("CardControllerAdapterTest")
@WebMvcTest(CardControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
public class CardControllerAdapterTest {
    private static final String GET_CARD ="";

    @Autowired
    private MockMvc restRequest;

    @MockBean
    private GetCard getCard;

    @Test
    @DisplayName("When Get card is success")
    void whenGetCardIsSuccess() throws Exception
    {
        when(getCard.getCard(Integer.parseInt(CardMock.STRING_VALUE))).thenReturn(CardMock.toCardDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_CARD))
                .andExpect(status().isOk());
    }





}
