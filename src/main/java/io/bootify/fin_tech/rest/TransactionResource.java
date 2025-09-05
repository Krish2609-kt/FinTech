package io.bootify.fin_tech.rest;

import io.bootify.fin_tech.model.TransactionDTO;
import io.bootify.fin_tech.service.AccountService;
import io.bootify.fin_tech.service.TransactionService;
import io.bootify.fin_tech.util.ReferencedException;
import io.bootify.fin_tech.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionResource {

    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionResource(final TransactionService transactionService,
            final AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(transactionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTransaction(
            @RequestBody @Valid final TransactionDTO transactionDTO) {
        final Long createdId = transactionService.create(transactionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTransaction(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TransactionDTO transactionDTO) {
        transactionService.update(id, transactionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaction(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = transactionService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fromAccountValues")
    public ResponseEntity<Map<Long, String>> getFromAccountValues() {
        return ResponseEntity.ok(accountService.getAccountValues());
    }

    @GetMapping("/toAccountValues")
    public ResponseEntity<Map<Long, String>> getToAccountValues() {
        return ResponseEntity.ok(accountService.getAccountValues());
    }

}
