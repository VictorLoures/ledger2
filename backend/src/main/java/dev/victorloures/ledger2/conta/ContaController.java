package dev.victorloures.ledger2.conta;

import dev.victorloures.ledger2.conta.dto.ContaRequest;
import dev.victorloures.ledger2.conta.dto.ContaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaResponse> criar(@Valid @RequestBody ContaRequest request) {
        ContaResponse resposta = contaService.criar(request);
        return ResponseEntity.created(URI.create("/api/contas/" + resposta.id())).body(resposta);
    }

    @GetMapping
    public List<ContaResponse> listar() {
        return contaService.listar();
    }

    @GetMapping("/{id}")
    public ContaResponse buscarPorId(@PathVariable UUID id) {
        return contaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ContaResponse atualizar(@PathVariable UUID id, @Valid @RequestBody ContaRequest request) {
        return contaService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        contaService.excluir(id);
    }
}
