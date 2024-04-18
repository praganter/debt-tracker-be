package dev.batuhanyetgin.debt.dto;

import dev.batuhanyetgin.debt.types.CycleType;
import dev.batuhanyetgin.debt.types.DebtType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtDto {
    private Long id;

    private String title;

    private String description;

    private CycleType cycle;

    private Long userId;

    private DebtType type;

    private List<DebtHistoryDto> DebtHistoryDtoList;
}