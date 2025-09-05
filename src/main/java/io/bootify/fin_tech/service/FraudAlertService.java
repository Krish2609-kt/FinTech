package io.bootify.fin_tech.service;

import io.bootify.fin_tech.domain.FraudAlert;
import io.bootify.fin_tech.domain.Transaction;
import io.bootify.fin_tech.model.FraudAlertDTO;
import io.bootify.fin_tech.repos.FraudAlertRepository;
import io.bootify.fin_tech.repos.TransactionRepository;
import io.bootify.fin_tech.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FraudAlertService {

    private final FraudAlertRepository fraudAlertRepository;
    private final TransactionRepository transactionRepository;

    public FraudAlertService(final FraudAlertRepository fraudAlertRepository,
            final TransactionRepository transactionRepository) {
        this.fraudAlertRepository = fraudAlertRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<FraudAlertDTO> findAll() {
        final List<FraudAlert> fraudAlerts = fraudAlertRepository.findAll(Sort.by("id"));
        return fraudAlerts.stream()
                .map(fraudAlert -> mapToDTO(fraudAlert, new FraudAlertDTO()))
                .toList();
    }

    public FraudAlertDTO get(final Long id) {
        return fraudAlertRepository.findById(id)
                .map(fraudAlert -> mapToDTO(fraudAlert, new FraudAlertDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FraudAlertDTO fraudAlertDTO) {
        final FraudAlert fraudAlert = new FraudAlert();
        mapToEntity(fraudAlertDTO, fraudAlert);
        return fraudAlertRepository.save(fraudAlert).getId();
    }

    public void update(final Long id, final FraudAlertDTO fraudAlertDTO) {
        final FraudAlert fraudAlert = fraudAlertRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fraudAlertDTO, fraudAlert);
        fraudAlertRepository.save(fraudAlert);
    }

    public void delete(final Long id) {
        fraudAlertRepository.deleteById(id);
    }

    private FraudAlertDTO mapToDTO(final FraudAlert fraudAlert, final FraudAlertDTO fraudAlertDTO) {
        fraudAlertDTO.setId(fraudAlert.getId());
        fraudAlertDTO.setAlertType(fraudAlert.getAlertType());
        fraudAlertDTO.setDetails(fraudAlert.getDetails());
        fraudAlertDTO.setStatus(fraudAlert.getStatus());
        fraudAlertDTO.setCreatedAt(fraudAlert.getCreatedAt());
        fraudAlertDTO.setTransaction(fraudAlert.getTransaction() == null ? null : fraudAlert.getTransaction().getId());
        return fraudAlertDTO;
    }

    private FraudAlert mapToEntity(final FraudAlertDTO fraudAlertDTO, final FraudAlert fraudAlert) {
        fraudAlert.setAlertType(fraudAlertDTO.getAlertType());
        fraudAlert.setDetails(fraudAlertDTO.getDetails());
        fraudAlert.setStatus(fraudAlertDTO.getStatus());
        fraudAlert.setCreatedAt(fraudAlertDTO.getCreatedAt());
        final Transaction transaction = fraudAlertDTO.getTransaction() == null ? null : transactionRepository.findById(fraudAlertDTO.getTransaction())
                .orElseThrow(() -> new NotFoundException("transaction not found"));
        fraudAlert.setTransaction(transaction);
        return fraudAlert;
    }

}
