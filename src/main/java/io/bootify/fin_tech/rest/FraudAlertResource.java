package io.bootify.fin_tech.rest;

import io.bootify.fin_tech.model.FraudAlertDTO;
import io.bootify.fin_tech.service.FraudAlertService;
import io.bootify.fin_tech.service.TransactionService;
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
@RequestMapping(value = "/api/fraudAlerts", produces = MediaType.APPLICATION_JSON_VALUE)
public class FraudAlertResource {

    private final FraudAlertService fraudAlertService;
    private final TransactionService transactionService;

    public FraudAlertResource(final FraudAlertService fraudAlertService,
            final TransactionService transactionService) {
        this.fraudAlertService = fraudAlertService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<FraudAlertDTO>> getAllFraudAlerts() {
        return ResponseEntity.ok(fraudAlertService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FraudAlertDTO> getFraudAlert(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(fraudAlertService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFraudAlert(
            @RequestBody @Valid final FraudAlertDTO fraudAlertDTO) {
        final Long createdId = fraudAlertService.create(fraudAlertDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFraudAlert(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FraudAlertDTO fraudAlertDTO) {
        fraudAlertService.update(id, fraudAlertDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFraudAlert(@PathVariable(name = "id") final Long id) {
        fraudAlertService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transactionValues")
    public ResponseEntity<Map<Long, String>> getTransactionValues() {
        return ResponseEntity.ok(transactionService.getTransactionValues());
    }

}
