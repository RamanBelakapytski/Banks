package by.tasks.application.domain.transaction;

import by.tasks.application.domain.account.Account;
import by.tasks.application.domain.bank.Bank;
import by.tasks.application.domain.bank.BankDao;
import by.tasks.application.domain.customer.Customer;
import by.tasks.application.domain.customer.CustomerDao;
import by.tasks.application.domain.customer.CustomerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FeeCalculatorTest {

    @Mock
    private BankDao bankDao;

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private FeeCalculator feeCalculator;

    private static Stream<Arguments> shouldCalculateFeeBasedOnBankAndCustomer() {
        return Stream.of(
                Arguments.of(BigDecimal.ONE, BigDecimal.TEN, CustomerType.LEGAL, BigDecimal.ONE),
                Arguments.of(BigDecimal.ONE, BigDecimal.TEN, CustomerType.NATURAL, BigDecimal.TEN)
        );
    }

    @Test
    void shouldCalculateZeroFeeForSameBank() {
        var bankId = UUID.randomUUID();
        assertEquals(BigDecimal.ZERO, feeCalculator.calculateFee(new Account.Builder().bankId(bankId).build(), new Account.Builder().bankId(bankId).build()));
    }

    @ParameterizedTest
    @MethodSource
    void shouldCalculateFeeBasedOnBankAndCustomer(
            BigDecimal bankLegalFee,
            BigDecimal bankNaturalFee,
            CustomerType customerType,
            BigDecimal expectedFee
    ) {
        var accountFrom = new Account.Builder()
                .bankId(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .build();

        var accountTo = new Account.Builder()
                .bankId(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .build();

        var customer = new Customer.Builder()
                .id(accountFrom.getCustomerId())
                .customerType(customerType)
                .build();

        var bank = new Bank.Builder()
                .id(accountFrom.getBankId())
                .fee(Map.of(CustomerType.LEGAL, bankLegalFee, CustomerType.NATURAL, bankNaturalFee))
                .build();

        given(bankDao.findById(bank.getId())).willReturn(Optional.of(bank));
        given(customerDao.findById(customer.getId())).willReturn(Optional.of(customer));

        assertEquals(expectedFee, feeCalculator.calculateFee(accountFrom, accountTo));
    }
}