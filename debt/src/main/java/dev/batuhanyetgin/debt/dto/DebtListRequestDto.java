package dev.batuhanyetgin.debt.dto;

import dev.batuhanyetgin.debt.types.DebtType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebtListRequestDto {
    private Long userId;
    private LocalDate date;
    private DebtType type;
    private int typeOrdinal;
}