package dev.victorloures.ledger2.pagamento;

import java.util.UUID;

public class PagamentoNotFoundException extends RuntimeException {

    public PagamentoNotFoundException(UUID id) {
        super("Pagamento não encontrado: " + id);
    }
}
