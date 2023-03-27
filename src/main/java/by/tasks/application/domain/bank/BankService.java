package by.tasks.application.domain.bank;

import by.tasks.application.BankApplicationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class BankService {
    private final BankDao bankDao;

    public BankService(BankDao bankDao) {
        this.bankDao = bankDao;
    }

    public Bank addNewBank(String name, BigDecimal legalFee, BigDecimal naturalFee) {
        if (bankDao.existsByName(name)) {
            throw new BankApplicationException("Bank with name `" + name + "` already exists");
        }
        return bankDao.save(new Bank(name, legalFee, naturalFee));
    }

    public Bank updateBank(UUID id, BigDecimal legalFee, BigDecimal naturalFee) {
        var bank = bankDao.findById(id).orElseThrow(() -> new BankApplicationException("Bank with id = " + id + " not found"));

        bank.setFee(legalFee, naturalFee);

        return bankDao.save(bank);
    }

    public List<Bank> findAll() {
        return bankDao.findAll();
    }
}
