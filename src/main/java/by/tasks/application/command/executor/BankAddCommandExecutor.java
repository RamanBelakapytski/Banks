package by.tasks.application.command.executor;

import by.tasks.application.BankApplicationException;
import by.tasks.application.domain.bank.BankService;

import java.math.BigDecimal;
import java.util.List;

public class BankAddCommandExecutor implements CommandExecutor {
    private final BankService bankService;

    public BankAddCommandExecutor(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public void execute(List<String> params) {
        if (params.size() != 3) {
            throw new BankApplicationException("Invalid parameters count");
        }
        var name = params.get(0);
        BigDecimal legalFee;
        BigDecimal naturalFee;

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

        System.out.println("Bank added: " + bankService.addNewBank(name, legalFee, naturalFee));
    }
}
