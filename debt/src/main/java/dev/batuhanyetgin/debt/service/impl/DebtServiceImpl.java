package dev.batuhanyetgin.debt.service.impl;

import dev.batuhanyetgin.debt.dto.*;
import dev.batuhanyetgin.debt.entity.DebtEntity;
import dev.batuhanyetgin.debt.entity.DebtHistoryEntity;
import dev.batuhanyetgin.debt.repository.DebtHistoryRepository;
import dev.batuhanyetgin.debt.repository.DebtRepository;
import dev.batuhanyetgin.debt.service.abstr.DebtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService {

    private final ModelMapper modelMapper;
    private final DebtRepository debtRepository;
    private final DebtHistoryRepository debtHistoryRepository;

    @Override
    public DebtCreateResponseDto createDebt(DebtCreateDto debtCreateDto) {

        DebtEntity newDebtEntity = debtRepository.save(DebtEntity.builder()
                .cycle(debtCreateDto.getCycle())
                .type(debtCreateDto.getType())
                .userId(debtCreateDto.getUserId())
                .description(debtCreateDto.getDescription())
                .title(debtCreateDto.getTitle())
                .build());
        DebtEntity debtEntity = debtRepository.save(newDebtEntity);
        log.info("New debt added with id: {}", debtEntity.getId());
        DebtHistoryEntity newDebtHistoryEntity = DebtHistoryEntity.builder()
                .debtEntity(debtEntity)
                .startDate(debtCreateDto.getStartDate())
                .endDate(debtCreateDto.getEndDate())
                .amount(debtCreateDto.getAmount())
                .build();
        DebtHistoryEntity debtHistoryEntity = debtHistoryRepository.save(newDebtHistoryEntity);
        log.info("New debt history added with id: {}", debtHistoryEntity.getId());

        return DebtCreateResponseDto.builder()
                .id(debtEntity.getId())
                .title(debtEntity.getTitle())
                .description(debtEntity.getDescription())
                .amount(debtHistoryEntity.getAmount())
                .build();
    }

    @Override
    public List<DebtDto> getDebtList(DebtListRequestDto debtListRequestDto) {
        List<DebtEntity> debtEntityList = debtRepository.getDebtList(debtListRequestDto);
        List<DebtDto> debtDtoList = debtEntityList.stream()
                .map(debtEntity -> {
                    DebtDto debtDto = modelMapper.map(debtEntity, DebtDto.class);
                    List<DebtHistoryDto> debtHistoryDtoList = debtEntity.getDebtHistoryEntityList().stream()
                            .map(debtHistoryEntity -> modelMapper.map(debtHistoryEntity, DebtHistoryDto.class))
                            .collect(Collectors.toList());
                    debtDto.setDebtHistoryDtoList(debtHistoryDtoList);
                    return debtDto;
                })
                .collect(Collectors.toList());

        log.info(String.valueOf(debtDtoList.size()));
        return debtDtoList;
    }

    @Override
    public void deleteDebt(Long debtId) {
        debtRepository.deleteById(debtId);
    }
}