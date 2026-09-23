package dev.victorloures.ledger2.conta.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaRequest(
        @NotBlank String nome,
        @NotNull @Positive BigDecimal valor,
        @NotNull @Min(1) @Max(31) Integer diaVencimento,
        String categoria,
        @NotNull Boolean recorrente,
        LocalDate dataVencimento,
        String observacoes
) {
}
