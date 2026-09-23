package dev.victorloures.ledger2.conta;

import dev.victorloures.ledger2.conta.dto.ContaRequest;
import dev.victorloures.ledger2.conta.dto.ContaResponse;
import dev.victorloures.ledger2.pagamento.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ContaService {

    private final ContaRepository contaRepository;
    private final PagamentoRepository pagamentoRepository;

    public ContaService(ContaRepository contaRepository, PagamentoRepository pagamentoRepository) {
        this.contaRepository = contaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public ContaResponse criar(ContaRequest request) {
        validarRecorrencia(request.recorrente(), request.dataVencimento());
        Conta conta = new Conta(
                request.nome(),
                request.valor(),
                request.diaVencimento(),
                request.categoria(),
                request.recorrente(),
                request.recorrente() ? null : request.dataVencimento(),
                request.observacoes()
        );
        contaRepository.save(conta);
        return toResponse(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaResponse> listar() {
        return contaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ContaResponse buscarPorId(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    public ContaResponse atualizar(UUID id, ContaRequest request) {
        validarRecorrencia(request.recorrente(), request.dataVencimento());
        Conta conta = buscarEntidade(id);
        conta.atualizar(
                request.nome(),
                request.valor(),
                request.diaVencimento(),
                request.categoria(),
                request.recorrente(),
                request.recorrente() ? null : request.dataVencimento(),
                request.observacoes()
        );
        return toResponse(conta);
    }

    public void excluir(UUID id) {
        Conta conta = buscarEntidade(id);
        contaRepository.delete(conta);
    }

    private Conta buscarEntidade(UUID id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new ContaNotFoundException(id));
    }

    private void validarRecorrencia(boolean recorrente, LocalDate dataVencimento) {
        if (!recorrente && dataVencimento == null) {
            throw new ContaInvalidaException("dataVencimento é obrigatória para contas não recorrentes");
        }
    }

    private ContaResponse toResponse(Conta conta) {
        LocalDate mesReferenciaAtual = YearMonth.now().atDay(1);
        boolean paga = conta.isRecorrente()
                ? pagamentoRepository.existsByConta_IdAndMesReferencia(conta.getId(), mesReferenciaAtual)
                : pagamentoRepository.existsByConta_Id(conta.getId());
        return new ContaResponse(
                conta.getId(),
                conta.getNome(),
                conta.getValor(),
                conta.getDiaVencimento(),
                conta.getCategoria(),
                conta.isRecorrente(),
                conta.getDataVencimento(),
                conta.getObservacoes(),
                paga,
                conta.getCriadoEm(),
                conta.getAtualizadoEm()
        );
    }
}
