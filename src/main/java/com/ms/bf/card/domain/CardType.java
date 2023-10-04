package com.ms.bf.card.domain;

import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Value
@Builder
@Component
public class CardType {


    String titular = "1";

    String additional = "2";

}
