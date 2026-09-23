package dev.victorloures.ledger2.conta.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ContaResponse(
        UUID id,
        String nome,
        BigDecimal valor,
        Integer diaVencimento,
        String categoria,
        boolean recorrente,
        LocalDate dataVencimento,
        String observacoes,
        boolean paga,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
