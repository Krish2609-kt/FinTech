package io.bootify.fin_tech.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;


public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String username;

    @NotNull
    @Size(max = 255)
    private String passwordHash;

    @NotNull
    @Size(max = 100)
    private String email;

    @Size(max = 15)
    private String phone;

    @Size(max = 100)
    private String fullName;

    @Size(max = 255)
    private String kycStatus;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private List<Long> userRoleRoles;

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

    public List<Long> getUserRoleRoles() {
        return userRoleRoles;
    }

    public void setUserRoleRoles(final List<Long> userRoleRoles) {
        this.userRoleRoles = userRoleRoles;
    }

}
