package io.bootify.fin_tech.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;


public class KycDocumentDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String docType;

    @NotNull
    @Size(max = 50)
    private String docNumber;

    @Size(max = 255)
    private String docFilePath;

    private Boolean verified;

    private OffsetDateTime uploadedAt;

    @NotNull
    private Long user;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(final String docType) {
        this.docType = docType;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(final String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocFilePath() {
        return docFilePath;
    }

    public void setDocFilePath(final String docFilePath) {
        this.docFilePath = docFilePath;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(final Boolean verified) {
        this.verified = verified;
    }

    public OffsetDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(final OffsetDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

}
