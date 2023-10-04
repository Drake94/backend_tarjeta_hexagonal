package com.ms.bf.card.application.port.out;

import com.ms.bf.card.domain.Account;

public interface AccountNumberRepository {
    Account getAccountNumber(Account account);


}
