package com.ms.bf.card.adapter.controller.card;
import com.ms.bf.card.adapter.controller.model.card.CardStatusRest;
import com.ms.bf.card.adapter.controller.model.card.CreateCardRest;
import com.ms.bf.card.application.port.in.CreateIn;
import com.ms.bf.card.application.port.in.UpdateCardStatus;
import lombok.extern.slf4j.Slf4j;

import com.ms.bf.card.adapter.controller.model.RestResponse;
import com.ms.bf.card.adapter.controller.model.card.CardRest;
import com.ms.bf.card.adapter.controller.processor.Processor;
import com.ms.bf.card.adapter.controller.processor.RequestProcessor;
import com.ms.bf.card.application.port.in.GetCard;
import com.ms.bf.card.domain.Card;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1/card/")
public class CardControllerAdapter {

    private static final String GET_CARD = "/{accountNumber}";
    private static final String CREATE_CARD = "/create";
    private static final String UPDATE_CARD = "/update/{cardNumber}";
    private final GetCard getCard;
    private final CreateIn createCardIn;
    private final Processor processor;
    private final UpdateCardStatus updateCard;


    public CardControllerAdapter(GetCard getCard, CreateIn createCardIn, UpdateCardStatus updateCard) {
        this.getCard = getCard;
        this.createCardIn = createCardIn;
        this.updateCard = updateCard;
        this.processor = new RequestProcessor();
    }
    @GetMapping(GET_CARD)
    public RestResponse<CardRest> getCard(final HttpServletRequest httpServletRequest, @Valid @PathVariable("accountNumber") String accountNumber)throws ExecutionException, InterruptedException  {
        log.info("Llamada al servicio card/{}", accountNumber);
        Card card =  this.getCard.getCard(accountNumber);
        CardRest response = CardRest.toCardRest(card);
        log.info("Respuesta del servicio /{}: [{}]", accountNumber, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
                res -> RestResponse.<CardRest>builder()
                        .data(response)
                        .id(res.getId())
                        .status(HttpStatus.OK.value())
                        .resource(httpServletRequest.getRequestURI())
                        .metadata(processor.buildMetadata(res.getReq()))
                        .build()
        );
    }

    @PostMapping(CREATE_CARD)
    public RestResponse<CardRest> createCard(final HttpServletRequest httpServletRequest, @Valid @RequestBody CardRest request )throws ExecutionException, InterruptedException{
        var response =  createCardIn.create(request.toCardDomain());
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
                res -> RestResponse.<CardRest>builder()
                        .data(CardRest.toCardRest(response))
                        .id(res.getId())
                       .status(HttpStatus.OK.value())
                       .resource(httpServletRequest.getRequestURI())
                       .metadata(processor.buildMetadata(res.getReq()))
                       .build()
        );

    }

    @PutMapping(UPDATE_CARD)
    public RestResponse<CardStatusRest> updateCard(@PathVariable("cardNumber") String cardNumber,final HttpServletRequest httpServletRequest, @RequestBody @Valid CardStatusRest request ) throws ExecutionException, InterruptedException, URISyntaxException {
        log.info("Llamada al servicio update/{}",cardNumber);
        var response =  updateCard.UpdateStatus(request.toCardStatusDomain());
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
                res -> RestResponse.<CardStatusRest>builder()
                        .data(CardStatusRest.toCardStatusRest(response))
                        .id(res.getId())
                        .status(HttpStatus.OK.value())
                        .resource(httpServletRequest.getRequestURI())
                        .metadata(processor.buildMetadata(res.getReq()))
                        .build()
        );
    }

}
