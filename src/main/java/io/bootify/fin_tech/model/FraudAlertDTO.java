package io.bootify.fin_tech.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;


public class FraudAlertDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String alertType;

    private String details;

    @Size(max = 255)
    private String status;

    private OffsetDateTime createdAt;

    @NotNull
    private Long transaction;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(final String alertType) {
        this.alertType = alertType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTransaction() {
        return transaction;
    }

    public void setTransaction(final Long transaction) {
        this.transaction = transaction;
    }

}
