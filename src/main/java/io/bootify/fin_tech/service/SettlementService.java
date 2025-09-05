package io.bootify.fin_tech.service;

import io.bootify.fin_tech.domain.Settlement;
import io.bootify.fin_tech.model.SettlementDTO;
import io.bootify.fin_tech.repos.SettlementRepository;
import io.bootify.fin_tech.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SettlementService {

    private final SettlementRepository settlementRepository;

    public SettlementService(final SettlementRepository settlementRepository) {
        this.settlementRepository = settlementRepository;
    }

    public List<SettlementDTO> findAll() {
        final List<Settlement> settlements = settlementRepository.findAll(Sort.by("id"));
        return settlements.stream()
                .map(settlement -> mapToDTO(settlement, new SettlementDTO()))
                .toList();
    }

    public SettlementDTO get(final Long id) {
        return settlementRepository.findById(id)
                .map(settlement -> mapToDTO(settlement, new SettlementDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SettlementDTO settlementDTO) {
        final Settlement settlement = new Settlement();
        mapToEntity(settlementDTO, settlement);
        return settlementRepository.save(settlement).getId();
    }

    public void update(final Long id, final SettlementDTO settlementDTO) {
        final Settlement settlement = settlementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(settlementDTO, settlement);
        settlementRepository.save(settlement);
    }

    public void delete(final Long id) {
        settlementRepository.deleteById(id);
    }

    private SettlementDTO mapToDTO(final Settlement settlement, final SettlementDTO settlementDTO) {
        settlementDTO.setId(settlement.getId());
        settlementDTO.setDate(settlement.getDate());
        settlementDTO.setTotalTransactions(settlement.getTotalTransactions());
        settlementDTO.setTotalAmount(settlement.getTotalAmount());
        settlementDTO.setStatus(settlement.getStatus());
        settlementDTO.setGeneratedAt(settlement.getGeneratedAt());
        return settlementDTO;
    }

    private Settlement mapToEntity(final SettlementDTO settlementDTO, final Settlement settlement) {
        settlement.setDate(settlementDTO.getDate());
        settlement.setTotalTransactions(settlementDTO.getTotalTransactions());
        settlement.setTotalAmount(settlementDTO.getTotalAmount());
        settlement.setStatus(settlementDTO.getStatus());
        settlement.setGeneratedAt(settlementDTO.getGeneratedAt());
        return settlement;
    }

}
