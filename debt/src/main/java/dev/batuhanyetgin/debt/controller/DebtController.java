package dev.batuhanyetgin.debt.controller;


import dev.batuhanyetgin.debt.dto.DebtCreateDto;
import dev.batuhanyetgin.debt.dto.DebtCreateResponseDto;
import dev.batuhanyetgin.debt.dto.DebtDto;
import dev.batuhanyetgin.debt.dto.DebtListRequestDto;
import dev.batuhanyetgin.debt.service.abstr.DebtService;
import dev.batuhanyetgin.debt.types.DebtType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/debt")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @PostMapping("/createDebt")
    ResponseEntity<DebtCreateResponseDto> createDebt(@RequestBody @Valid DebtCreateDto debtCreateDto) {
        return ResponseEntity.ok(debtService.createDebt(debtCreateDto));
    }

    @GetMapping("/getDebtList")
    ResponseEntity<List<DebtDto>> getDebtList(@RequestParam @Valid Long userId, @RequestParam @Valid LocalDate date, @RequestParam @Valid DebtType type) {
        return ResponseEntity.ok(debtService.getDebtList(new DebtListRequestDto(userId, date, type, type.ordinal())));
    }

    @DeleteMapping("/deleteDebt/{id}")
    void deleteDebt(@PathVariable Long id) {
        debtService.deleteDebt(id);
    }
}