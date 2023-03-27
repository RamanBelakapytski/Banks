package by.tasks.application.domain.account;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    UUID id;
    UUID userId;
    String currency;
    BigDecimal balance;
}
