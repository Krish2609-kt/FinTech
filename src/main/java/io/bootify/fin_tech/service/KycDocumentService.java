package io.bootify.fin_tech.service;

import io.bootify.fin_tech.domain.KycDocument;
import io.bootify.fin_tech.domain.User;
import io.bootify.fin_tech.model.KycDocumentDTO;
import io.bootify.fin_tech.repos.KycDocumentRepository;
import io.bootify.fin_tech.repos.UserRepository;
import io.bootify.fin_tech.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class KycDocumentService {

    private final KycDocumentRepository kycDocumentRepository;
    private final UserRepository userRepository;

    public KycDocumentService(final KycDocumentRepository kycDocumentRepository,
            final UserRepository userRepository) {
        this.kycDocumentRepository = kycDocumentRepository;
        this.userRepository = userRepository;
    }

    public List<KycDocumentDTO> findAll() {
        final List<KycDocument> kycDocuments = kycDocumentRepository.findAll(Sort.by("id"));
        return kycDocuments.stream()
                .map(kycDocument -> mapToDTO(kycDocument, new KycDocumentDTO()))
                .toList();
    }

    public KycDocumentDTO get(final Long id) {
        return kycDocumentRepository.findById(id)
                .map(kycDocument -> mapToDTO(kycDocument, new KycDocumentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final KycDocumentDTO kycDocumentDTO) {
        final KycDocument kycDocument = new KycDocument();
        mapToEntity(kycDocumentDTO, kycDocument);
        return kycDocumentRepository.save(kycDocument).getId();
    }

    public void update(final Long id, final KycDocumentDTO kycDocumentDTO) {
        final KycDocument kycDocument = kycDocumentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(kycDocumentDTO, kycDocument);
        kycDocumentRepository.save(kycDocument);
    }

    public void delete(final Long id) {
        kycDocumentRepository.deleteById(id);
    }

    private KycDocumentDTO mapToDTO(final KycDocument kycDocument,
            final KycDocumentDTO kycDocumentDTO) {
        kycDocumentDTO.setId(kycDocument.getId());
        kycDocumentDTO.setDocType(kycDocument.getDocType());
        kycDocumentDTO.setDocNumber(kycDocument.getDocNumber());
        kycDocumentDTO.setDocFilePath(kycDocument.getDocFilePath());
        kycDocumentDTO.setVerified(kycDocument.getVerified());
        kycDocumentDTO.setUploadedAt(kycDocument.getUploadedAt());
        kycDocumentDTO.setUser(kycDocument.getUser() == null ? null : kycDocument.getUser().getId());
        return kycDocumentDTO;
    }

    private KycDocument mapToEntity(final KycDocumentDTO kycDocumentDTO,
            final KycDocument kycDocument) {
        kycDocument.setDocType(kycDocumentDTO.getDocType());
        kycDocument.setDocNumber(kycDocumentDTO.getDocNumber());
        kycDocument.setDocFilePath(kycDocumentDTO.getDocFilePath());
        kycDocument.setVerified(kycDocumentDTO.getVerified());
        kycDocument.setUploadedAt(kycDocumentDTO.getUploadedAt());
        final User user = kycDocumentDTO.getUser() == null ? null : userRepository.findById(kycDocumentDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        kycDocument.setUser(user);
        return kycDocument;
    }

}
