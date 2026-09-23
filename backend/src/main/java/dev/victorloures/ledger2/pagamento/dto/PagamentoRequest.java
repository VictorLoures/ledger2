package dev.victorloures.ledger2.pagamento.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoRequest(
        @NotNull LocalDate mesReferencia,
        @NotNull LocalDate dataPagamento,
        @NotNull @Positive BigDecimal valorPago,
        String observacoes
) {
}
