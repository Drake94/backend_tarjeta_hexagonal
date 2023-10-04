package com.ms.bf.card.adapter.controller.model.card;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.domain.Account;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountRest {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    String accountNumber;

    @JsonCreator
    public AccountRest(@JsonProperty("numero-cuenta") String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public AccountRest() {}
    public static AccountRest toAccountRest(Account account) {
        return AccountRest.builder()
                .accountNumber(Objects.nonNull(account.getAccountNumber()) ? account.getAccountNumber() : "")
                .build();
    }
}
