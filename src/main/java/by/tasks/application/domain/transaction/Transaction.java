package by.tasks.application.domain.transaction;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {
    UUID id;
    BigDecimal amount;
    String currency;
    UUID from;
    UUID to;
}
