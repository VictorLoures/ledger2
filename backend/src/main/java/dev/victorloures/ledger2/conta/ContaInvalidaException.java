package dev.victorloures.ledger2.conta;

public class ContaInvalidaException extends RuntimeException {

    public ContaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
