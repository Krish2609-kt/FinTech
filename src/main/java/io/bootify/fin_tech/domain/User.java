package io.bootify.fin_tech.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "\"User\"")
public class User {

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

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String fullName;

    @Column
    private String kycStatus;

    @Column
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "UserRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> userRoleRoles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Account> userAccounts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<KycDocument> userKycDocuments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(final String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getUserRoleRoles() {
        return userRoleRoles;
    }

    public void setUserRoleRoles(final Set<Role> userRoleRoles) {
        this.userRoleRoles = userRoleRoles;
    }

    public Set<Account> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(final Set<Account> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public Set<KycDocument> getUserKycDocuments() {
        return userKycDocuments;
    }

    public void setUserKycDocuments(final Set<KycDocument> userKycDocuments) {
        this.userKycDocuments = userKycDocuments;
    }

}
