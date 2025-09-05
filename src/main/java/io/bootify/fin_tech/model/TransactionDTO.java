package io.bootify.fin_tech.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;


public class TransactionDTO {

    private Long id;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "92.08")
    private BigDecimal amount;

    @Size(max = 3)
    private String currency;

    @Size(max = 255)
    private String status;

    @NotNull
    @Size(max = 255)
    private String transactionType;

    private OffsetDateTime initiatedAt;

    private OffsetDateTime completedAt;

    @NotNull
    private Long fromAccount;

    @NotNull
    private Long toAccount;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(final String transactionType) {
        this.transactionType = transactionType;
    }

    public OffsetDateTime getInitiatedAt() {
        return initiatedAt;
    }

    public void setInitiatedAt(final OffsetDateTime initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(final OffsetDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(final Long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public void setToAccount(final Long toAccount) {
        this.toAccount = toAccount;
    }

}
