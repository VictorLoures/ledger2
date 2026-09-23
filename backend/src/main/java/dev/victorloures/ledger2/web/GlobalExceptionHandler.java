package dev.victorloures.ledger2.web;

import dev.victorloures.ledger2.conta.ContaInvalidaException;
import dev.victorloures.ledger2.conta.ContaNotFoundException;
import dev.victorloures.ledger2.pagamento.PagamentoDuplicadoException;
import dev.victorloures.ledger2.pagamento.PagamentoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ContaNotFoundException.class, PagamentoNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpoErro(ex.getMessage()));
    }

    @ExceptionHandler(ContaInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleInvalida(ContaInvalidaException ex) {
        return ResponseEntity.badRequest().body(corpoErro(ex.getMessage()));
    }

    @ExceptionHandler(PagamentoDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicado(PagamentoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(corpoErro(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacao(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", Instant.now(),
                "erros", erros
        ));
    }

    private Map<String, Object> corpoErro(String mensagem) {
        return Map.of(
                "timestamp", Instant.now(),
                "mensagem", mensagem
        );
    }
}
