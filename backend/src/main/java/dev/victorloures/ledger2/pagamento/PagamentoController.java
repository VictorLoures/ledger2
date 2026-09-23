package dev.victorloures.ledger2.pagamento;

import dev.victorloures.ledger2.pagamento.dto.PagamentoRequest;
import dev.victorloures.ledger2.pagamento.dto.PagamentoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contas/{contaId}/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> registrar(@PathVariable UUID contaId,
                                                         @Valid @RequestBody PagamentoRequest request) {
        PagamentoResponse resposta = pagamentoService.registrar(contaId, request);
        return ResponseEntity.created(
                URI.create("/api/contas/" + contaId + "/pagamentos/" + resposta.id())
        ).body(resposta);
    }

    @GetMapping
    public List<PagamentoResponse> listar(@PathVariable UUID contaId) {
        return pagamentoService.listarPorConta(contaId);
    }

    @DeleteMapping("/{pagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID contaId, @PathVariable UUID pagamentoId) {
        pagamentoService.excluir(contaId, pagamentoId);
    }
}
