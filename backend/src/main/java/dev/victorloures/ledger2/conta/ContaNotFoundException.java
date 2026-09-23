package dev.victorloures.ledger2.conta;

import java.util.UUID;

public class ContaNotFoundException extends RuntimeException {

    public ContaNotFoundException(UUID id) {
        super("Conta não encontrada: " + id);
    }
}
