package dev.batuhanyetgin.debt.dto;

import dev.batuhanyetgin.debt.types.CycleType;
import dev.batuhanyetgin.debt.types.DebtType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtCreateDto {
    @NotNull
    @NotBlank
    private Long userId;
    @NotNull
    @NotBlank
    private String title;
    private String description;
    @NotNull
    @NotBlank
    private Double amount;
    @NotNull
    @NotBlank
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull
    @NotBlank
    private CycleType cycle;
    @NotNull
    @NotBlank
    private DebtType type;
}