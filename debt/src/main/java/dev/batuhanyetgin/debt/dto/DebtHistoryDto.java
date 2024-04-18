package dev.batuhanyetgin.debt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtHistoryDto {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private double amount;

    private Long debtId;
}