package dev.batuhanyetgin.debt.service.abstr;

import dev.batuhanyetgin.debt.dto.DebtCreateDto;
import dev.batuhanyetgin.debt.dto.DebtCreateResponseDto;
import dev.batuhanyetgin.debt.dto.DebtDto;
import dev.batuhanyetgin.debt.dto.DebtListRequestDto;

import java.util.List;

public interface DebtService {
    DebtCreateResponseDto createDebt(DebtCreateDto debtCreateDto);

    List<DebtDto> getDebtList(DebtListRequestDto debtListRequestDto);

    void deleteDebt(Long debtId);
}