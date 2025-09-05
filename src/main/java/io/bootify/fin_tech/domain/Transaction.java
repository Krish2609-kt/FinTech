package io.bootify.fin_tech.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Transaction {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(length = 3)
    private String currency;

    @Column
    private String status;

    @Column(nullable = false)
    private String transactionType;

    @Column
    private OffsetDateTime initiatedAt;

    @Column
    private OffsetDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @OneToMany(mappedBy = "transaction")
    private Set<FraudAlert> transactionFraudAlerts = new HashSet<>();

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

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(final Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(final Account toAccount) {
        this.toAccount = toAccount;
    }

    public Set<FraudAlert> getTransactionFraudAlerts() {
        return transactionFraudAlerts;
    }

    public void setTransactionFraudAlerts(final Set<FraudAlert> transactionFraudAlerts) {
        this.transactionFraudAlerts = transactionFraudAlerts;
    }

}
