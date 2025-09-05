package io.bootify.fin_tech.service;

import io.bootify.fin_tech.domain.Account;
import io.bootify.fin_tech.domain.Transaction;
import io.bootify.fin_tech.domain.User;
import io.bootify.fin_tech.model.AccountDTO;
import io.bootify.fin_tech.repos.AccountRepository;
import io.bootify.fin_tech.repos.TransactionRepository;
import io.bootify.fin_tech.repos.UserRepository;
import io.bootify.fin_tech.util.CustomCollectors;
import io.bootify.fin_tech.util.NotFoundException;
import io.bootify.fin_tech.util.ReferencedWarning;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(final AccountRepository accountRepository,
            final UserRepository userRepository,
            final TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<AccountDTO> findAll() {
        final List<Account> accounts = accountRepository.findAll(Sort.by("id"));
        return accounts.stream()
                .map(account -> mapToDTO(account, new AccountDTO()))
                .toList();
    }

    public AccountDTO get(final Long id) {
        return accountRepository.findById(id)
                .map(account -> mapToDTO(account, new AccountDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AccountDTO accountDTO) {
        final Account account = new Account();
        mapToEntity(accountDTO, account);
        return accountRepository.save(account).getId();
    }

    public void update(final Long id, final AccountDTO accountDTO) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(accountDTO, account);
        accountRepository.save(account);
    }

    public void delete(final Long id) {
        accountRepository.deleteById(id);
    }

    private AccountDTO mapToDTO(final Account account, final AccountDTO accountDTO) {
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCurrency(account.getCurrency());
        accountDTO.setCreatedAt(account.getCreatedAt());
        accountDTO.setUser(account.getUser() == null ? null : account.getUser().getId());
        return accountDTO;
    }

    private Account mapToEntity(final AccountDTO accountDTO, final Account account) {
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setBalance(accountDTO.getBalance());
        account.setCurrency(accountDTO.getCurrency());
        account.setCreatedAt(accountDTO.getCreatedAt());
        final User user = accountDTO.getUser() == null ? null : userRepository.findById(accountDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        account.setUser(user);
        return account;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Transaction fromAccountTransaction = transactionRepository.findFirstByFromAccount(account);
        if (fromAccountTransaction != null) {
            referencedWarning.setKey("account.transaction.fromAccount.referenced");
            referencedWarning.addParam(fromAccountTransaction.getId());
            return referencedWarning;
        }
        final Transaction toAccountTransaction = transactionRepository.findFirstByToAccount(account);
        if (toAccountTransaction != null) {
            referencedWarning.setKey("account.transaction.toAccount.referenced");
            referencedWarning.addParam(toAccountTransaction.getId());
            return referencedWarning;
        }
        return null;
    }

    public Map<Long, String> getAccountValues() {
        return accountRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Account::getId, Account::getAccountNumber));
    }

}
