package by.tasks.application.command.executor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.bank.BankService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class BankUpdateCommandExecutor implements CommandExecutor {
    private final BankService bankService;

    public BankUpdateCommandExecutor(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public void execute(List<String> params) {
        if (params.size() != 3) {
            throw new BankApplicationException("Invalid parameters count");
        }
        UUID id;
        BigDecimal legalFee;
        BigDecimal naturalFee;

        try {
            id = UUID.fromString(params.get(0));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for BANK_ID");
        }

        try {
            legalFee = new BigDecimal(params.get(1));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for LEGAL_FEE");
        }

        try {
            naturalFee = new BigDecimal(params.get(2));
        } catch (Exception e) {
            throw new BankApplicationException("Invalid format for NATURAL_FEE");
        }

        System.out.println("Bank added: " + bankService.updateBank(id, legalFee, naturalFee));
    }
}
