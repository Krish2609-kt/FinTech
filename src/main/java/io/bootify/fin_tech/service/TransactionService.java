package io.bootify.fin_tech.service;

import io.bootify.fin_tech.domain.Account;
import io.bootify.fin_tech.domain.FraudAlert;
import io.bootify.fin_tech.domain.Transaction;
import io.bootify.fin_tech.model.TransactionDTO;
import io.bootify.fin_tech.repos.AccountRepository;
import io.bootify.fin_tech.repos.FraudAlertRepository;
import io.bootify.fin_tech.repos.TransactionRepository;
import io.bootify.fin_tech.util.CustomCollectors;
import io.bootify.fin_tech.util.NotFoundException;
import io.bootify.fin_tech.util.ReferencedWarning;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final FraudAlertRepository fraudAlertRepository;

    public TransactionService(final TransactionRepository transactionRepository,
            final AccountRepository accountRepository,
            final FraudAlertRepository fraudAlertRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.fraudAlertRepository = fraudAlertRepository;
    }

    public List<TransactionDTO> findAll() {
        final List<Transaction> transactions = transactionRepository.findAll(Sort.by("id"));
        return transactions.stream()
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .toList();
    }

    public TransactionDTO get(final Long id) {
        return transactionRepository.findById(id)
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TransactionDTO transactionDTO) {
        final Transaction transaction = new Transaction();
        mapToEntity(transactionDTO, transaction);
        return transactionRepository.save(transaction).getId();
    }

    public void update(final Long id, final TransactionDTO transactionDTO) {
        final Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transactionDTO, transaction);
        transactionRepository.save(transaction);
    }

    public void delete(final Long id) {
        transactionRepository.deleteById(id);
    }

    private TransactionDTO mapToDTO(final Transaction transaction,
            final TransactionDTO transactionDTO) {
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setCurrency(transaction.getCurrency());
        transactionDTO.setStatus(transaction.getStatus());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setInitiatedAt(transaction.getInitiatedAt());
        transactionDTO.setCompletedAt(transaction.getCompletedAt());
        transactionDTO.setFromAccount(transaction.getFromAccount() == null ? null : transaction.getFromAccount().getId());
        transactionDTO.setToAccount(transaction.getToAccount() == null ? null : transaction.getToAccount().getId());
        return transactionDTO;
    }

    private Transaction mapToEntity(final TransactionDTO transactionDTO,
            final Transaction transaction) {
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setInitiatedAt(transactionDTO.getInitiatedAt());
        transaction.setCompletedAt(transactionDTO.getCompletedAt());
        final Account fromAccount = transactionDTO.getFromAccount() == null ? null : accountRepository.findById(transactionDTO.getFromAccount())
                .orElseThrow(() -> new NotFoundException("fromAccount not found"));
        transaction.setFromAccount(fromAccount);
        final Account toAccount = transactionDTO.getToAccount() == null ? null : accountRepository.findById(transactionDTO.getToAccount())
                .orElseThrow(() -> new NotFoundException("toAccount not found"));
        transaction.setToAccount(toAccount);
        return transaction;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final FraudAlert transactionFraudAlert = fraudAlertRepository.findFirstByTransaction(transaction);
        if (transactionFraudAlert != null) {
            referencedWarning.setKey("transaction.fraudAlert.transaction.referenced");
            referencedWarning.addParam(transactionFraudAlert.getId());
            return referencedWarning;
        }
        return null;
    }

    public Map<Long, String> getTransactionValues() {
        return transactionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Transaction::getId, Transaction::getTransactionType));
    }

}
