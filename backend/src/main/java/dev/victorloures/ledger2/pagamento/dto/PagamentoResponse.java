package dev.victorloures.ledger2.pagamento.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record PagamentoResponse(
        UUID id,
        LocalDate mesReferencia,
        LocalDate dataPagamento,
        BigDecimal valorPago,
        String observacoes,
        Instant criadoEm
) {
}
