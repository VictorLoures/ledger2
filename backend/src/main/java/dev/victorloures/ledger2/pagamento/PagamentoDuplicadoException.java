package dev.victorloures.ledger2.pagamento;

import java.time.LocalDate;

public class PagamentoDuplicadoException extends RuntimeException {

    public PagamentoDuplicadoException(LocalDate mesReferencia) {
        super("Já existe pagamento registrado para o mês de referência " + mesReferencia);
    }
}
