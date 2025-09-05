package io.bootify.fin_tech.rest;

import io.bootify.fin_tech.model.AccountDTO;
import io.bootify.fin_tech.service.AccountService;
import io.bootify.fin_tech.service.UserService;
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
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountResource {

    private final AccountService accountService;
    private final UserService userService;

    public AccountResource(final AccountService accountService, final UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(accountService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAccount(@RequestBody @Valid final AccountDTO accountDTO) {
        final Long createdId = accountService.create(accountDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAccount(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AccountDTO accountDTO) {
        accountService.update(id, accountDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAccount(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = accountService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(userService.getUserValues());
    }

}
