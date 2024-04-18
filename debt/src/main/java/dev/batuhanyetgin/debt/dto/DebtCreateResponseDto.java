package dev.batuhanyetgin.debt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebtCreateResponseDto {
    private Long id;
    private String title;
    private String description;
    private double amount;
}